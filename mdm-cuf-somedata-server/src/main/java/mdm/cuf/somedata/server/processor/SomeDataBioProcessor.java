package mdm.cuf.somedata.server.processor;

import mdm.cuf.core.api.CufPushRequest;
import mdm.cuf.core.messages.Message;
import mdm.cuf.core.server.processor.AbstractBioProcessor;
import mdm.cuf.somedata.server.dio.repository.SomeDataDioRepository;
import mdm.cuf.somedata.server.processor.transformer.SomeDataBioDioTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mdm.cuf.somedata.bio.SomeDataBio;
import mdm.cuf.somedata.server.dio.SomeDataDio;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * class to add extra business logic in case extra functionality is needed in top of core
 * @author darias
 */

@Service
public class SomeDataBioProcessor extends AbstractBioProcessor<SomeDataBio, SomeDataDio, Long> {

    @Autowired
    SomeDataDioRepository someDataDioRepository;

    @Autowired
    SomeDataBioDioTransformer someDataBioDioTransformer;

    private static final Logger LOGGER = LoggerFactory.getLogger(SomeDataBioProcessor.class);

    @Override
    public List<Message> pushExecutePreProcessing(CufPushRequest<SomeDataBio> request, Class<?>... validationHints) {
        final List<Message> errorList = new ArrayList<>();

        mdm.cuf.core.bio.Bio coreBio = request.getBio();
        SomeDataBio bio = new SomeDataBio();
        bio.setSourceSystem(coreBio.getSourceSystem());
        bio.setOriginatingSourceSystem(coreBio.getOriginatingSourceSystem());
        bio.setSourceSystemUser(coreBio.getSourceSystemUser());
        bio.setCreateDate(coreBio.getSourceDate());
        bio.setSourceDate(coreBio.getSourceDate());
        bio.setUpdateDate(coreBio.getSourceDate());

        setSomeDataDio(bio);

        return errorList;
    }

    protected void setSomeDataDio(SomeDataBio bio){
        SomeDataDio dio = new SomeDataDio();
        long id = new Random().nextLong();
        id = (id < 0) ? -id : id;
        dio.setSomeDataId(id);
        String desc = "demo" + dio.getSomeDataId().toString();
        dio.setDemoField(desc);
        dio.setOriginatingSourceSystem(bio.getOriginatingSourceSystem());
        dio.setSourceDate(bio.getSourceDate());
        dio.setSourceSystem(bio.getSourceSystem());
        dio.setSourceSystemUser(bio.getSourceSystemUser());
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        dio.setTxAuditId(uuidAsString);
        dio.setCreateDate(bio.getCreateDate());
        dio.setUpdateDate(bio.getUpdateDate());
        someDataDioRepository.save(dio);
    }

}

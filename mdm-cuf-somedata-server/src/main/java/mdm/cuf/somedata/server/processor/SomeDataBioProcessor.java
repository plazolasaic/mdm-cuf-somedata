package mdm.cuf.somedata.server.processor;

import mdm.cuf.core.api.CufPushRequest;
import mdm.cuf.core.messages.Message;
import mdm.cuf.somedata.server.dio.repository.SomeDataDioRepository;
import mdm.cuf.somedata.server.processor.transformer.SomeDataBioDioTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mdm.cuf.somedata.bio.SomeDataBio;
import mdm.cuf.somedata.server.dio.SomeDataDio;
import mdm.cuf.core.server.processor.AbstractBioProcessor;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Message> pushExecutePreProcessing(CufPushRequest<SomeDataBio> request, Class<?>... validationHints) {

        final List<Message> errorList = new ArrayList<>();

        SomeDataBio bio = request.getBio();
        SomeDataDio dio = someDataBioDioTransformer.bioToDio(bio);
        long id = dio.getSomeDataId();
        SomeDataDio foundDio = someDataDioRepository.findById(id);
        if(foundDio == null) {
            setSomeDataDio(request.getBio());
        }
        return errorList;
    }

    protected void setSomeDataDio(SomeDataBio bio){
        SomeDataDio dio = new SomeDataDio();
        dio.setSomeDataId(bio.getSomeDataId());
        dio.setOriginatingSourceSystem(bio.getOriginatingSourceSystem());
        dio.setSourceDate(bio.getSourceDate());
        dio.setSourceSystem(bio.getSourceSystem());
        dio.setSourceSystemUser(bio.getSourceSystemUser());
        dio.setTxAuditId(bio.getTxAuditId());
        dio.setCreateDate(bio.getCreateDate());
        dio.setUpdateDate(bio.getUpdateDate());
        someDataDioRepository.save(dio);
    }

}

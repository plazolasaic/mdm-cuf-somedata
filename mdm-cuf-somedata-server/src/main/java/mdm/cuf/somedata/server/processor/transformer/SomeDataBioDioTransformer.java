package mdm.cuf.somedata.server.processor.transformer;

import org.springframework.stereotype.Component;

import mdm.cuf.core.server.processor.AbstractBioDioTransformer;
import mdm.cuf.somedata.bio.SomeDataBio;
import mdm.cuf.somedata.server.dio.SomeDataDio;


/**
 * Transformer for SomeData Bio's and Dio's.
 * @author darias
 */
@Component
public class SomeDataBioDioTransformer extends AbstractBioDioTransformer<SomeDataBio, SomeDataDio> {

    @Override
    public SomeDataDio bioToDio(SomeDataBio bio) {
    	final SomeDataDio dio = bioToDioAllProperties(bio, new SomeDataDio());
        dio.setSomeDataId(bio.getSomeDataId());
        dio.setDemoField(bio.getDemoField());
        return dio;
    }

    @Override
    public SomeDataBio dioToBio(SomeDataDio dio) {
    	final SomeDataBio bio = dioToBioAllProperties(dio, new SomeDataBio());
        bio.setSomeDataId(dio.getSomeDataId());
        bio.setDemoField(dio.getDemoField());
        return bio;
    }

}

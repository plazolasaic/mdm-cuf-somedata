package mdm.cuf.somedata.server.processor;

import org.springframework.stereotype.Service;

import mdm.cuf.somedata.bio.SomeDataBio;
import mdm.cuf.somedata.server.dio.SomeDataDio;
import mdm.cuf.core.server.processor.AbstractBioProcessor;

/**
 * class to add extra business logic in case extra functionality is needed in top of core
 * @author darias
 */

@Service
public class SomeDataBioProcessor extends AbstractBioProcessor<SomeDataBio, SomeDataDio, Long> {

}

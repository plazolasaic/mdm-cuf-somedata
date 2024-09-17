package mdm.cuf.somedata.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import mdm.cuf.somedata.server.dio.SomeDataDio;
import mdm.cuf.core.server.persist.dio.Dio;
import mdm.cuf.core.server.security.hub.AbstractCufSecuritySeeder;
import mdm.cuf.core.server.security.hub.CufDio;
import mdm.cuf.core.server.security.hub.CufSystem;
import mdm.cuf.core.server.security.hub.CufSystemDioPermissionRepository;

/**
 * This class prepares the security setup for the tests.  This will create the test system and assign rw access
 * to test system to allow full permissions on the hierarchy of DIOs, create a read only cuf system with read access
 * to all DIOs, and create a test cuf admin system.
 * 
 * @author darias
 */
@Component
public class SomeDataServerSecuritySeeder extends AbstractCufSecuritySeeder {
    
    public static final String CUF_DEMO_SYSTEM_NAME = "DEMO_SYSTEM";
    
    @Override
    protected List<CufSystem> getSystemsToSeed() {
        final CufSystem testSystem = new CufSystem();
        testSystem.setCufSystemName(CUF_DEMO_SYSTEM_NAME);
        
        final List<CufSystem> systemsToSeed = new ArrayList<>();
        systemsToSeed.add(testSystem);
        return systemsToSeed;
    }

    @Override
    protected List<Class<? extends Dio<?>>> getDiosToSeed() {
        final List<Class<? extends Dio<?>>> diosToSeed = new ArrayList<>();
        diosToSeed.add(SomeDataDio.class);
        return diosToSeed;
    }
    
    @Override
    protected void setupPermissions(CufSystemDioPermissionRepository cufSystemDioPermissionRepository, List<CufSystem> cufSystems,
            List<CufDio> cufDios) {
        for(CufSystem cufSystem: cufSystems){
            if(CUF_DEMO_SYSTEM_NAME.equals(cufSystem.getCufSystemName())){
                super.giveReadWriteToAll(Arrays.asList(new CufSystem[]{cufSystem}), cufDios);
            } 
        }
    }
    
    @Override
    protected Map<String, String> getCommonNamesToSeed() {
        return new HashMap<>();
    }

}

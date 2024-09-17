package mdm.cuf.somedata.db;

import mdm.cuf.core.db.AbstractMdmCufCoreDbTest;

/**
 * This unit test executes the database schema scripts against an in-memory H2 database in order
 * to enable CI verification of changes to the core VaProfile database schema. This will let us know before the next
 * deployment if there are issues with the database scripts.
 * 
 * @author darias
 */
public class SomeDataDb_UnitTest extends AbstractMdmCufCoreDbTest {
    
    /** liquibase change log path for mdm-cuf-core-db project */ 
    private static final String LIQUIBASE_CHANGE_LOG_PATH = "classpath:/cuf-somedata-database/mdm.cuf.somedata-master-changelog.xml";
    
    /** liquibase change log path for mdm-cuf-core-db project */ 
    @Override
    public String getChangeLogPath() {
        return LIQUIBASE_CHANGE_LOG_PATH;
    }

}

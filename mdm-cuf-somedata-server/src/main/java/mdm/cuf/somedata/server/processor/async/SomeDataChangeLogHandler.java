package mdm.cuf.somedata.server.processor.async;

import mdm.cuf.core.api.async.AsyncStatus;
import mdm.cuf.core.api.async.CufAsyncHandlerResponse;
import mdm.cuf.core.api.async.CufChangeLogInstruction;
import mdm.cuf.core.bio.Bio;
import mdm.cuf.core.server.logging.LogUtil;
import mdm.cuf.core.server.processor.async.changelog.ChangeLogHandler;
import mdm.cuf.somedata.bio.SomeDataBio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * A PendingUpdateErrorHandler implementation that will receive CufPendingUpdateInstruction messages from the pending update error topic.
 *
 * @author jshrader
 */
@Component
@Primary
public class SomeDataChangeLogHandler implements ChangeLogHandler {

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SomeDataChangeLogHandler.class);

    int counter = 0;

    @Override
    public CufAsyncHandlerResponse handle(CufChangeLogInstruction cufChangeLogInstruction) {
        Bio bio = cufChangeLogInstruction.getBio();
        String bioStr = (null == bio) ? "no bio." : bio.toString();
        LogUtil.logInfoWithBanner(LOGGER, "msg #"+ ++counter + " SomeData Change Log Handler",
                "The following CufPendingUpdateInstruction was RECEIVED in this handler with bio: " + bioStr);
        CufAsyncHandlerResponse response = new CufAsyncHandlerResponse();
        response.setStatus(AsyncStatus.COMPLETED_SUCCESS);
        return response;
    }

}

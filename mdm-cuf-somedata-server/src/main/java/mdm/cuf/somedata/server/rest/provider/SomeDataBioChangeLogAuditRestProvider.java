package mdm.cuf.somedata.server.rest.provider;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mdm.cuf.core.api.ServiceResponse;
import mdm.cuf.core.rest.OpenAPICommon;
import mdm.cuf.core.server.processor.async.changelog.audit.ChangeLogAuditRestProvider;

@RequestMapping(value = SomeDataBioRestProvider.URL_PREFIX)
@RestController
@Tag(name=SomeDataBioChangeLogAuditRestProvider.TAG, description = SomeDataBioChangeLogAuditRestProvider.DESCRIPTION)
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = OpenAPICommon.MESSAGE_200 ),
        @ApiResponse(responseCode = "400", description = OpenAPICommon.MESSAGE_400, content = { @Content(schema =  @Schema(implementation = ServiceResponse.class)) } ),
        @ApiResponse(responseCode = "403", description = OpenAPICommon.MESSAGE_403, content = { @Content(schema =  @Schema(implementation = ServiceResponse.class)) } ),
        @ApiResponse(responseCode = "404", description = OpenAPICommon.MESSAGE_404, content = { @Content(schema =  @Schema(implementation = ServiceResponse.class)) } ),
        @ApiResponse(responseCode = "429", description = OpenAPICommon.MESSAGE_429, content = { @Content(schema =  @Schema(implementation = ServiceResponse.class)) } ),
        @ApiResponse(responseCode = "500", description = OpenAPICommon.MESSAGE_500, content = { @Content(schema =  @Schema(implementation = ServiceResponse.class)) })
})
public class SomeDataBioChangeLogAuditRestProvider extends ChangeLogAuditRestProvider {

    /** The tag used in swagger documentation. */
    public static final String TAG = "SomeDataBioChangeLogAudit" ;
    
    /** The description that shows up in swagger documentation. */
    public static final String DESCRIPTION = "";
}

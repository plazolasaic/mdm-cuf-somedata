package mdm.cuf.somedata.server.dio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.envers.Audited;

import mdm.cuf.core.server.persist.dio.AbstractDio;

/**
 * The somedata data information object.
 *
 * @author darias
 */
@Entity
@Table(name = "SOMEDATA")
@SequenceGenerator(name = "someDataIdSeq", sequenceName = "SOMEDATA_S", allocationSize = 1)
@Audited
public class SomeDataDio extends AbstractDio<Long>{

	/** The somedata id. */
	private Long someDataId;
	
	private String demoField;

	@Id
    @GeneratedValue(generator = "someDataIdSeq", strategy = GenerationType.SEQUENCE)
    @Column(name = "SOMEDATA_ID", nullable = false)
    public Long getSomeDataId() {
        return someDataId;
    }
    
    public void setSomeDataId(final Long someDataId) {
        this.someDataId = someDataId;
    }
    
    @Column(name = "DEMO_FIELD", nullable = true)
    public String getDemoField() {
        return demoField;
    }

	public void setDemoField(String demoField) {
	    this.demoField = demoField;
	}

	@Override
    @Transient
    public Long getUniqueId() {
        return getSomeDataId();
    }

}

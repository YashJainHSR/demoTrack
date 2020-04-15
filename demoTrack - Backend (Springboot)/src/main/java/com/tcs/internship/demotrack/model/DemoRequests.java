package com.tcs.internship.demotrack.model;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class DemoRequests {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	private String requestNumber;
	@NotBlank()
	private String requestTimestamp;
	@NotBlank()
	private String requesterUserId;
	@NotBlank()
	private String requesterName;
	@NotBlank(message = "Customer Name cannot be blank")
	private String customerName;
	@NotBlank(message = "Customer Email cannot be blank")
	@Email(message = "Invalid Email")
	private String customerEmail;
	@NotBlank(message = "Customer Location cannot be blank")
	private String customerLocation;
	@NotBlank(message = "Customer Business Line cannot be blank")
	private String customerBusinessLine;
	@NotBlank(message = "Contact Person's Name cannot be blank")
	private String contactPersonName;
	@NotBlank(message = "Contact Person's Email cannot be blank")
	@Email(message = "Invalid Email")
	private String contactPersonEmail;
	@NotBlank(message = "Product Group cannot be blank")
	private String productGroup;
	@NotBlank(message = "Product Line cannot be blank")
	private String productLine;
	@NotBlank(message = "Product cannot be blank")
	private String product;
	@NotBlank(message = "Site cannot be blank")
	private String demoSite;
	@NotBlank(message = "Demo Date cannot be blank")
	@DateTimeFormat(pattern = "mm-dd-yyyy")
	private String demoDate;
	@NotBlank(message = "Slot cannot be blank")
	private String demoSlot;
	@NotNull
	private Integer numberOfGuests;
	@Length(max = 100, min = 10)
	private String demoExpectation;
	@NotNull
	private String aseDressCode;
	@NotNull
	private Integer aseTechnicalKnowledgeRating;
	@NotNull
	private Integer aseClinicalKnowledgeRating;
	@NotNull
	private String demoSitePrepared;
	@NotNull
	private Integer aseCustomerHandlingRating;
	@NotNull
	private Integer siteAmbianceRating;
	@NotNull
	private Integer productRating;
	@NotNull
	private String customerConvinced;
	@NotNull
	private String feedback;
	@NotNull
	private String comment;
	@NotBlank(message = "Status cannot be blank")
	private String status;
	@NotNull
	private String authority;
	
	public DemoRequests() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DemoRequests(Long id, String requestNumber, String requestTimestamp, String requesterUserId,
			String requesterName, String customerName, String customerEmail, String customerLocation,
			String customerBusinessLine, String contactPersonName, String contactPersonEmail, String productGroup,
			String productLine, String product, String demoSite, String demoDate, String demoSlot,
			Integer numberOfGuests, String demoExpectation, String aseDressCode, Integer aseTechnicalKnowledgeRating,
			Integer aseClinicalKnowledgeRating, String demoSitePrepared, Integer aseCustomerHandlingRating,
			Integer siteAmbianceRating, Integer productRating, String customerConvinced, String feedback,
			String comment, String status, String authority) {
		super();
		this.id = id;
		this.requestNumber = requestNumber;
		this.requestTimestamp = requestTimestamp;
		this.requesterUserId = requesterUserId;
		this.requesterName = requesterName;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerLocation = customerLocation;
		this.customerBusinessLine = customerBusinessLine;
		this.contactPersonName = contactPersonName;
		this.contactPersonEmail = contactPersonEmail;
		this.productGroup = productGroup;
		this.productLine = productLine;
		this.product = product;
		this.demoSite = demoSite;
		this.demoDate = demoDate;
		this.demoSlot = demoSlot;
		this.numberOfGuests = numberOfGuests;
		this.demoExpectation = demoExpectation;
		this.aseDressCode = aseDressCode;
		this.aseTechnicalKnowledgeRating = aseTechnicalKnowledgeRating;
		this.aseClinicalKnowledgeRating = aseClinicalKnowledgeRating;
		this.demoSitePrepared = demoSitePrepared;
		this.aseCustomerHandlingRating = aseCustomerHandlingRating;
		this.siteAmbianceRating = siteAmbianceRating;
		this.productRating = productRating;
		this.customerConvinced = customerConvinced;
		this.feedback = feedback;
		this.comment = comment;
		this.status = status;
		this.authority = authority;
	}

	
	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public Long getId() {
		return id;
	}

	public String getRequesterUserId() {
		return requesterUserId;
	}

	public String getStatus() {
		return status;
	}

	public String getAuthority() {
		return authority;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DemoRequests other = (DemoRequests) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

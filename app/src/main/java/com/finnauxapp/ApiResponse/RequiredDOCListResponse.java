package com.finnauxapp.ApiResponse;

public class RequiredDOCListResponse {


    /**
     * ApplicationId : 11
     * CustomerId : 23
     * Customer : rajesh sharma
     * Category : KYC
     * DocName : PAN Card
     * DocFileName : null
     * Status : Pending
     */

    private int ApplicationId;
    private int CustomerId;
    private String Customer;
    private String Category;
    private String DocName;


    public int getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(int documentId) {
        DocumentId = documentId;
    }

    private int DocumentId;
    private String DocFileName;

    public String getDocNo() {
        return DocNo;
    }

    public void setDocNo(String docNo) {
        DocNo = docNo;
    }

    private String DocNo;
    private String Status;

    public String getBaseImageString() {
        return baseImageString;
    }

    public void setBaseImageString(String baseImageString) {
        this.baseImageString = baseImageString;
    }

    private String baseImageString;

    public int getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(int ApplicationId) {
        this.ApplicationId = ApplicationId;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public String getCustomer() {
        return Customer;
    }

    public void setCustomer(String Customer) {
        this.Customer = Customer;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getDocName() {
        return DocName;
    }

    public void setDocName(String DocName) {
        this.DocName = DocName;
    }

    public String getDocFileName() {
        return DocFileName;
    }

    public void setDocFileName(String DocFileName) {
        this.DocFileName = DocFileName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}

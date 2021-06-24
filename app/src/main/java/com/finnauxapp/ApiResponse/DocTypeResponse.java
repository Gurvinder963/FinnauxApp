package com.finnauxapp.ApiResponse;

public class DocTypeResponse {


    /**
     * DocId : 1
     * Category : KYC
     * Documnet : PAN Card
     * DocCatId : 1
     */

    private int DocId;
    private String Category;
    private String Documnet;
    private int DocCatId;

    public int getDocId() {
        return DocId;
    }

    public void setDocId(int DocId) {
        this.DocId = DocId;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getDocumnet() {
        return Documnet;
    }

    public void setDocumnet(String Documnet) {
        this.Documnet = Documnet;
    }

    public int getDocCatId() {
        return DocCatId;
    }

    public void setDocCatId(int DocCatId) {
        this.DocCatId = DocCatId;
    }
}

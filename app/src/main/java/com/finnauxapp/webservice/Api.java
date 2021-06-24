package com.finnauxapp.webservice;


import com.finnauxapp.ApiRequest.ApplicationDetailRequest;
import com.finnauxapp.ApiRequest.ApplicationDocSaveRequest;
import com.finnauxapp.ApiRequest.BranchRequest;
import com.finnauxapp.ApiRequest.CalculateIRRRequest;
import com.finnauxapp.ApiRequest.ClientCodeRequest;
import com.finnauxapp.ApiRequest.CompleteProcessAppRequest;
import com.finnauxapp.ApiRequest.CustomerDetailRequest;
import com.finnauxapp.ApiRequest.CustomerDuplicationRequest;
import com.finnauxapp.ApiRequest.CustomerKYCDocRequest;
import com.finnauxapp.ApiRequest.CustomerMemberRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.CustomerRegistrationRequest;
import com.finnauxapp.ApiRequest.DashBoardDataRequest;
import com.finnauxapp.ApiRequest.DeleteCustomerRequest;
import com.finnauxapp.ApiRequest.DeleteKYCRequest;
import com.finnauxapp.ApiRequest.DistrictRequest;
import com.finnauxapp.ApiRequest.DocMasterRequest;
import com.finnauxapp.ApiRequest.ExistingLoanSaveRequest;
import com.finnauxapp.ApiRequest.ExpenditureSaveRequest;
import com.finnauxapp.ApiRequest.FirmRequestModel;
import com.finnauxapp.ApiRequest.GenerateApplicationRequest;
import com.finnauxapp.ApiRequest.GenerateOTPRequest;
import com.finnauxapp.ApiRequest.GetCustomerAddressRequest;
import com.finnauxapp.ApiRequest.KYCDocSaveRequest;
import com.finnauxapp.ApiRequest.LeadRequest;
import com.finnauxapp.ApiRequest.LoginFeeRequest;
import com.finnauxapp.ApiRequest.LoginRequest;
import com.finnauxapp.ApiRequest.PartnerRequest;
import com.finnauxapp.ApiRequest.ReferenceSaveRequest;
import com.finnauxapp.ApiRequest.RejectRequest;
import com.finnauxapp.ApiRequest.SaveEnquiryRequest;
import com.finnauxapp.ApiRequest.SaveHoleRequest;
import com.finnauxapp.ApiRequest.SaveIncomeDetailRequest;
import com.finnauxapp.ApiRequest.SaveOTPVerifyRequest;
import com.finnauxapp.ApiRequest.SavePartnerRequestModel;
import com.finnauxapp.ApiRequest.SaveRevertRequest;
import com.finnauxapp.ApiRequest.SchemeRequest;
import com.finnauxapp.ApiRequest.SendMessageRequest;
import com.finnauxapp.ApiRequest.TahsilRequest;
import com.finnauxapp.ApiRequest.UploadApplicationDocRequest;
import com.finnauxapp.ApiRequest.UploadCustomerDocRequest;
import com.finnauxapp.ApiRequest.UploadPicRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.ApplicationDetailResponse;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
import com.finnauxapp.ApiResponse.AssetListResponse;
import com.finnauxapp.ApiResponse.CalculateIRRResponse;
import com.finnauxapp.ApiResponse.ClientCodeResponse;
import com.finnauxapp.ApiResponse.CustomerAddressResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.CustomerExpenditureResponse;
import com.finnauxapp.ApiResponse.CustomerMemberListResponse;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.ApiResponse.DistrictResponse;
import com.finnauxapp.ApiResponse.DocTypeResponse;
import com.finnauxapp.ApiResponse.DuplicationCheckResponse;
import com.finnauxapp.ApiResponse.EmployeeBranchResponse;
import com.finnauxapp.ApiResponse.ExistingLoanResponse;
import com.finnauxapp.ApiResponse.FinancialDetailResponse;
import com.finnauxapp.ApiResponse.FirmResponse;
import com.finnauxapp.ApiResponse.GenerateOTPResponse;
import com.finnauxapp.ApiResponse.HoldLeadResponse;
import com.finnauxapp.ApiResponse.IncomeDetailResponse;
import com.finnauxapp.ApiResponse.KYCDocListResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.ApiResponse.LeadSouseResponse;
import com.finnauxapp.ApiResponse.LoginResponse;
import com.finnauxapp.ApiResponse.PartnerResponse;
import com.finnauxapp.ApiResponse.ProductTypeResponse;
import com.finnauxapp.ApiResponse.QuestionAnswerResponse;
import com.finnauxapp.ApiResponse.ReferenceResponse;
import com.finnauxapp.ApiResponse.RequiredDOCListResponse;
import com.finnauxapp.ApiResponse.SchemeResponse;
import com.finnauxapp.ApiResponse.StateResponse;
import com.finnauxapp.ApiResponse.TahsilResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface Api {

    String BASE_URL = "http://admin.finnaux.com/";

    @POST("api/api/MobileLogin/LOS_GetUserLoginApp")
    Call<LoginResponse> getLoginDetails(@Body LoginRequest model);


    @POST("api/api/Mobile/LOSGetDashboardCountAPP")
    Call<List<DashboardDataResponse>> getDashBoardDetails(@Body DashBoardDataRequest model);

    @POST("api/api/Mobile/LOS_GetInquiryListApp")
    Call<List<LeadResponse>> getLeadList(@Body LeadRequest model);

    @POST("api/api/Masters/GetProductForDropdown")
    Call<ArrayList<ProductTypeResponse>> getLoadProductType();


    @POST("api/api/Mobile/LOS_RejectEnquireAPP")
    Call<List<AcceptRejectResponse>> getRejectLead(@Body RejectRequest model);

    @POST("api/api/LOS/LOS_SaveGenerateApplicationNo")
    Call<List<AcceptRejectResponse>> getGenerateLead(@Body CustomerRegMainRequest model);

    @POST("api/api/Mobile/LOS_GetApplicationListApp")
    Call<List<ApplicationListResponse>> getApplicationList(@Body DashBoardDataRequest model);

    @POST("api/api/Mobile/LOS_GetApplicationDetailsApp")
    Call<ApplicationDetailResponse> getApplicationDetail(@Body ApplicationDetailRequest model);

    @POST("api/api/Mobile/LOS_SaveApplicationCustomerApp")
    Call<List<AcceptRejectResponse>> saveAppCustomer(@Body CustomerRegMainRequest model);

    @POST("api/api/Mobile/LOS_CheckCustomerDuplicationKYCApp")
    Call<DuplicationCheckResponse> checkCustomerDuplication(@Body CustomerDuplicationRequest model);

    @POST("UploadDoc/api/UploadDoc/UploadCustomerDoc")
    Call<Boolean> uploadCustomerDOC(@Body UploadCustomerDocRequest model);

    @POST("api/api/Masters/GetDocumentMaster")
    Call<List<DocTypeResponse>> getDocumentMaster(@Body DocMasterRequest model);

    @POST("api/api/Mobile/LOS_CompleteProcessAPP")
    Call<List<AcceptRejectResponse>> completeProcessAPP(@Body CompleteProcessAppRequest model);

    @POST("api/api/LOS/LOS_SaveNEWEnquire")
    Call<List<AcceptRejectResponse>> saveNewEnquiry(@Body CustomerRegMainRequest model);

    @POST("api/api/Mobile/LOSGetLeadSourceForDropdown")
    Call<List<LeadSouseResponse>> getLeadSourse();

    @POST("uploadDoc/api/UploadDoc/UploadProfilePic")
    Call<Boolean> uploadCustomerProfilePic(@Body UploadPicRequest model);



    @POST("api/api/Masters/GetState")
    Call<List<StateResponse>> getState();

    @POST("api/api/Masters/GetDistricts")
    Call<List<DistrictResponse>> getDistricts(@Body DistrictRequest model);

    @POST("api/api/Masters/GetTahsil")
    Call<List<TahsilResponse>> getTahsil(@Body TahsilRequest model);


    @POST("api/api/Mobile/Get_CustomerDetailsApp")
    Call<List<CustomerDetailResponse>> getCustomerDetail(@Body CustomerDetailRequest model);

    @POST("api/api/Mobile/LOS_SaveCustomerAddressApp")
    Call<List<AcceptRejectResponse>> saveCustomerAddress(@Body CustomerRegMainRequest model);

    @POST("api/api/Mobile/Get_CustomerAddressApp")
    Call<CustomerAddressResponse> getCustomerAddress(@Body GetCustomerAddressRequest model);

    @POST("api/api/Mobile/LOS_UpdateCustomerDetailsApp")
    Call<List<AcceptRejectResponse>> updateCustomerDetails(@Body CustomerRegMainRequest model);

    @POST("api/api/Mobile/LOS_GetCustomerKYCDocApp")
    Call<KYCDocListResponse> getCustomerKYCDoc(@Body CustomerKYCDocRequest model);

    @POST("api/api/Mobile/LOS_SaveCustomerKYCDocApp")
    Call<List<AcceptRejectResponse>> saveCustomerKYCDoc(@Body KYCDocSaveRequest model);

    @POST("api/api/Mobile/LOS_GetCustomerMemberApp")
    Call<List<CustomerMemberListResponse>> getCustomerMemberApp(@Body CustomerMemberRequest model);

    @POST("api/api/Mobile/LOS_SaveCustomerMemberApp")
    Call<List<AcceptRejectResponse>> saveCustomerMemberApp(@Body CustomerRegMainRequest model);

    @POST("api/api/Mobile/LOS_GetCustomerExpenditureApp")
    Call<List<CustomerExpenditureResponse>> getCustomerExpenditureApp(@Body CustomerDetailRequest model);

    @POST("api/api/Mobile/LOS_SaveCustomerExpenditureApp")
    Call<List<AcceptRejectResponse>> saveCustomerExpenditureApp(@Body ExpenditureSaveRequest model);

    @POST("api/api/Mobile/LOS_GetCustomerReferenceApp")
    Call<List<ReferenceResponse>> getCustomerReferenceApp(@Body CustomerDetailRequest model);

    @POST("api/api/Mobile/LOS_SaveCustomerReferenceApp")
    Call<List<AcceptRejectResponse>> saveCustomerReferenceApp(@Body ReferenceSaveRequest model);


    @POST("api/api/Mobile/LOS_GetCustomerExistingLoanApp")
    Call<List<ExistingLoanResponse>> getCustomerExistingLoanApp(@Body CustomerDetailRequest model);

    @POST("api/api/Mobile/LOS_SaveLOSCustomerExistingLoanApp")
    Call<List<AcceptRejectResponse>> saveCustomerExistingLoanApp(@Body ExistingLoanSaveRequest model);

    @POST("api/api/Mobile/LOS_SaveCustomerIncomeDetailsApp")
    Call<List<AcceptRejectResponse>> saveCustomerIncomeDetailsApp(@Body CustomerRegMainRequest model);

    @POST("api/api/Mobile/LOS_GetCustomerIncomeApp")
    Call<List<IncomeDetailResponse>> getCustomerIncomeDetailsApp(@Body CustomerDetailRequest model);

    @POST("api/api/Mobile/LOS_SaveLoginFeeApp")
    Call<List<AcceptRejectResponse>> saveLoginFeeApp(@Body LoginFeeRequest model);

    @POST("api/api/Mobile/LOS_GetFIApplicationListApp")
    Call<List<ApplicationListResponse>> getApplicationListFI(@Body DashBoardDataRequest model);

    @POST("api/api/Mobile/LOS_DeleteApplicationCustomer")
    Call<List<AcceptRejectResponse>> getDeleteApplicationCustomer(@Body DeleteCustomerRequest model);

    @POST("api/api/Mobile/LOS_DeleteCustomerDocument")
    Call<List<AcceptRejectResponse>> getDeleteCustomerDocument(@Body DeleteKYCRequest model);

    @POST("api/api/Mobile//LOS_ValidateSalesProcessApp")
    Call<List<AcceptRejectResponse>> getValidateSalesProcessApp(@Body ApplicationDetailRequest model);

    @POST("api/api/Mobile/GetApplicationsSendBackBranchApp")
    Call<List<ApplicationListResponse>> getApplicationsSendBackBranchApp(@Body DashBoardDataRequest model);

    @POST("api/api/Mobile/LOS_GetFIQuestionsApp")
    Call<List<QuestionAnswerResponse>> getLOS_GetFIQuestionsApp(@Body CustomerDetailRequest model);

    @POST("api/api/Mobile/LOS_SaveCustomerFIAnswerApp")
    Call<List<AcceptRejectResponse>> saveCustomerFIAnswerApp(@Body CustomerRegMainRequest model);

    @POST("api/api/Mobile/LOS_PendingDocApplication")
    Call<List<ApplicationListResponse>> getLOS_PendingDocApplication(@Body DashBoardDataRequest model);

    @POST("api/api/Mobile/LOS_GetAssetDetailApp")
    Call<List<AssetListResponse>> getLOS_GetAssetDetailAppp(@Body ApplicationDetailRequest model);

    @POST("api/api/Mobile/LOS_SaveAssetDetailsApp")
    Call<List<AcceptRejectResponse>> saveAssetDetailsApp(@Body CustomerRegMainRequest model);

    @POST("UploadDoc/api/UploadDoc/UploadApplicationDoc")
    Call<Boolean> uploadApplicationDOC(@Body UploadApplicationDocRequest model);

    @POST("api/api/Mobile/LOS_GetApplicationRequiredDocListApp")
    Call<List<RequiredDOCListResponse>> getApplicationRequiredDocListApp(@Body ApplicationDetailRequest model);

    @POST("api/api/Mobile/LOS_SaveDeviceInformationAPP")
    Call<List<AcceptRejectResponse>> saveMobileInfoApp(@Body CustomerRegMainRequest model);

    @POST("api/api/Mobile/LOS_GetCompletedApplicationListApp")
    Call<List<ApplicationListResponse>> getCompletedApplicationList(@Body DashBoardDataRequest model);

    @POST("api/api/Mobile/LOS_GetFICompletedApplicationListApp")
    Call<List<ApplicationListResponse>> getFICompletedApplicationList(@Body DashBoardDataRequest model);

    @POST("api/api/LOS/Get_PartnerForDropdown")
    Call<List<PartnerResponse>> getPartnerForDropdown(@Body PartnerRequest model);


    @POST("api/api/LOS/LOS_SaveAppliactionDocument")
    Call<List<AcceptRejectResponse>> saveApplicationDoc(@Body ApplicationDocSaveRequest model);


    @POST("api/api/LOS/LOS_SaveHoldInquiry")
    Call<List<AcceptRejectResponse>> saveHoldLead(@Body SaveHoleRequest model);

    @POST("api/api/LOS/LOS_SaveRevertInquiry")
    Call<List<AcceptRejectResponse>> saveRevertLead(@Body SaveRevertRequest model);

    @POST("api/api/Mobile/LOS_GetHoldInquiryListApp")
    Call<List<LeadResponse>> getHoldLead(@Body DashBoardDataRequest model);

    @POST("api/api/Masters/Get_EmployeeBranchForDropdown")
    Call<List<EmployeeBranchResponse>> getEmployeeBranch(@Body BranchRequest model);

    @POST("api/api/Masters/Generate_OTPPhoneNoVerification")
    Call<List<GenerateOTPResponse>> generateOTP(@Body GenerateOTPRequest model);

    @POST("api/api/Masters/SAVE_OTP_Verification")
    Call<List<AcceptRejectResponse>> saveOTPVerification(@Body SaveOTPVerifyRequest model);

    @POST("api/api/Masters/SendSMS")
    Call<Integer> sendSMS(@Body SendMessageRequest model);


    @POST("api/api/Mobile/LOS_Save_ApplicationCustomerFirmApp")
    Call<List<AcceptRejectResponse>> saveAppFirm(@Body CustomerRegMainRequest model);

    @POST("api/api/Mobile/LOS_Save_FirmPartnersApp")
    Call<List<AcceptRejectResponse>> saveFirmPartnersAp(@Body SavePartnerRequestModel model);

    @POST("api/api/Mobile/GetFirmPartnersAPP")
    Call<List<FirmResponse>> getFirmPartnerApp(@Body FirmRequestModel model);

    @POST("api/api/Result/Get_ClientAPIDetails")
    Call<List<ClientCodeResponse>> getClientAPIDetails(@Body ClientCodeRequest model);

    @POST("api/api/Masters/Get_SchemeList")
    Call<List<SchemeResponse>> getSchemeList(@Body SchemeRequest model);

    @POST("api/api/LOS/LOS_GetCalculateEMIIRR")
    Call<List<CalculateIRRResponse>> getCalculateEMIIRR(@Body CalculateIRRRequest model);


    @POST("api/api/LOS/FinancialDetailsForUpdate")
    Call<FinancialDetailResponse> getFinancialDetailsForUpdate(@Body ApplicationDetailRequest model);

    @POST("api/api/LOS/LOS_UpdateApplicationFinancialDetails")
    Call<List<AcceptRejectResponse>> getUpdateApplicationFinancialDetails(@Body CustomerRegMainRequest model);


}

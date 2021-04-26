package in.amruthashala.momapp.inputresponsemodel;

public class BankAccountDetails {
    public String account_holder_name;
    public String account_no;
    public String ifsc_code;
    public String bank_name;
    public String branch_name;

    public BankAccountDetails(String account_holder_name, String account_no, String ifsc_code, String bank_name, String branch_name) {
        this.account_holder_name = account_holder_name;
        this.account_no = account_no;
        this.ifsc_code = ifsc_code;
        this.bank_name = bank_name;
        this.branch_name = branch_name;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public void setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }
}

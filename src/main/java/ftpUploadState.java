public class ftpUploadState {
    private Agency agency;
    private boolean uploading;
    private int toUpload;
    private int uploaded;
    private int errors;
    private double uploadSpeed;
    private double currentPercentage;
    private double totalPercentage;

    public ftpUploadState() {
    }

    public ftpUploadState(Agency a){
        this.agency = a;
    }

    public synchronized  Agency getAgency() {
        return agency;
    }

    public synchronized  void setAgency(Agency agency) {
        this.agency = agency;
    }

    public synchronized  int getToUpload() {
        return toUpload;
    }

    public synchronized  void setToUpload(int toUpload) {
        this.toUpload = toUpload;
    }

    public synchronized  int getUploaded() {
        return uploaded;
    }

    public synchronized  void setUploaded(int uploaded) {
        this.uploaded = uploaded;
    }

    public synchronized  int getErrors() {
        return errors;
    }

    public synchronized  void setErrors(int errors) {
        this.errors = errors;
    }

    public synchronized double getUploadSpeed() {
        return uploadSpeed;
    }

    public synchronized void setUploadSpeed(double uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    public synchronized double getCurrentPercentage() {
        return currentPercentage;
    }

    public synchronized void setCurrentPercentage(double currentPercentage) {
        this.currentPercentage = currentPercentage;
    }

    public synchronized double getTotalPercentage() {
        return totalPercentage;
    }

    public synchronized void setTotalPercentage(double totalPercentage) {
        this.totalPercentage = totalPercentage;
    }

    public boolean isUploading() {
        return uploading;
    }

    public void setUploading(boolean uploading) {
        this.uploading = uploading;
    }
}

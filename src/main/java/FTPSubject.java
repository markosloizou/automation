public interface FTPSubject {
    void attachObserver(FTPObserver observer);
    void detachObserver(FTPObserver observer);
    void Notify();
}

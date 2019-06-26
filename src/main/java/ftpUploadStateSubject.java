import java.util.ArrayList;

public class ftpUploadStateSubject implements FTPSubject {
    private ArrayList<FTPObserver> observers;
    private static ftpUploadStateSubject instance = null;
    ArrayList<ftpUploadState> states;

    @Override
    public synchronized  void attachObserver(FTPObserver observer) {
        if(observers.contains(observer)) return;
        observers.add(observer);
    }

    @Override
    public synchronized  void detachObserver(FTPObserver observer) {
        if(observers.contains(observer)) observers.remove(observer);
    }

    //todo add agency in notify call
    @Override
    public synchronized  void Notify(Agency a) {
        for(FTPObserver o:observers){
            o.update(a);
        }
    }

    @Override
    public void Notify() {
        for(FTPObserver o:observers){
            o.update();
        }
    }

    public synchronized  static ftpUploadStateSubject getInstance(){
        if(instance == null){
            instance = new ftpUploadStateSubject();
        }
        return instance;
    }

    public ftpUploadStateSubject() {
        Agency[] agencies =  Agency.values();
        for(Agency a:agencies){
            states.add(new ftpUploadState(a));
        }
    }

    public synchronized ftpUploadState getAgencyUploadState(Agency a){
        for(ftpUploadState s:states){
            if(s.getAgency().equals(a)) return s;
        }
        return null;
    }

    //Set state from other class
    public synchronized  void setState(ftpUploadState state) {
        for(ftpUploadState s:states){
            //replace state
            if(s.getAgency().equals(state.getAgency())){
                states.set(states.indexOf(s),state);
                Notify(state.getAgency());
            }
        }
    }
}

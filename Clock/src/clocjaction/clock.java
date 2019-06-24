package clocjaction;

public class clock {
	private diaplay hour=new diaplay(24);
	private diaplay minute=new diaplay(60);
    public void start() {
    	while(true) {
    		minute.increase();
    	if(minute.getValue()==0) {
    		hour.increase();
    	}
    	System.out.printf("%02d:%02d\n",hour.getValue(),minute.getValue());
    	}
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		clock clock=new clock();
		clock.start();
 
	}

}

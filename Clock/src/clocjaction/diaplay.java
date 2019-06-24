package clocjaction;

public class diaplay {
	private int value=0;
	private int limit=0;

	public diaplay(int limit)
	{
		this.limit=limit;
	}
	public void increase() {
		value++;
		if(value==limit) {
			value=0;
		}
	}
	public int getValue() {
		return value;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         diaplay d= new diaplay(24);
         for (;;) {
	          d.increase();
              System.out.println(d.getValue());
}
	}

}

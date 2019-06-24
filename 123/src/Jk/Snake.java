package Jk;
import java.awt.Color;
import java.awt.Graphics; 
import java.awt.Toolkit; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.InputEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.KeyListener; 

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
public class Snake extends JFrame implements ActionListener, KeyListener,Runnable { 

    private static final long serialVersionUID = 1L;
    /**
     * �˵���
     */
    private JMenuBar menuBar; 
    /**
     * ��Ϸ���Ѷȣ����������ڲ˵���
     */
    private JMenu youXiMenu,nanDuMenu,fenShuMenu,guanYuMenu; 
    /**
     * ��ʼ��Ϸ��������Ϸ���������ߣ���߷���
     */
    private JMenuItem kaiShiYouXi,exitItem,zuoZheItem,fenShuItem; 
    /**
     * ���ѳ̶��е��Ӳ˵�
     */
    private JCheckBoxMenuItem cJianDan,cPuTong,cKunNan; 
    /**
     * �߳���
     */
    private int length = 10; 
    private Toolkit toolkit; //���������þ��Ƿ�������
    /**
     * x:��ͷ����x�����
     * y:��ͷ����y�����
     * z:��ǰ������4:->;3:<-;2:�£�1����
     * object��objectX��objectY:ʳ��������������ɵ�ʳ������
     * growth������һ��0��û�У�1:��
     * time:��һ����ʱ��
     */
    private int i,x,y,z,objectX,objectY,object=0,growth=0,time;
/**
 * ��¼����ÿ���ڵ�����λ��
 */
    private int m[]=new int[50]; 
    private int n[]=new int[50]; 
    /**
     * �߶���
     */
    private Thread she = null;
    /**
     * �Ե���ʳ������
     */
    private int foods = 0; 
    /**
     * ��ǰ����
     */
    private int life=0; 

    /**
     * ��߷���
     */
    private int fenshu=0; 

public void run(){  
  time=500; 
  for(i=0;i<=length-1;i++) 
  { 
   m[i]=90-i*10;n[i]=60; 
  } 
  
  x=m[0]; 
  y=n[0]; 
  z=4; 
  
  
  while(she!=null) 
  { 
   
   check(); 
   try 
   { 
    Thread.sleep(time); 
   } 
   catch(Exception ee) 
   { 
    System.out.println(z+""); 
   } 
  } 
} 
/**
 * �������Բ˵�������
 */
public Snake() { 
 //-----------�˵�������--------------------//
  setVisible(true); 
  menuBar = new JMenuBar(); 
  toolkit=getToolkit(); 
  youXiMenu = new JMenu("��Ϸ"); 
  kaiShiYouXi = new JMenuItem("��ʼ��Ϸ"); 
  exitItem = new JMenuItem("�˳���Ϸ"); 
  nanDuMenu = new JMenu("���ѳ̶�"); 
  cJianDan = new JCheckBoxMenuItem("��"); 
  cPuTong = new JCheckBoxMenuItem("��ͨ"); 
  cKunNan = new JCheckBoxMenuItem("����"); 
  fenShuMenu = new JMenu("��������"); 
  fenShuItem = new JMenuItem("��߼�¼"); 
  guanYuMenu = new JMenu("����"); 
  zuoZheItem = new JMenuItem("��������"); 
  guanYuMenu.add(zuoZheItem); 
  nanDuMenu.add(cJianDan); 
  nanDuMenu.add(cPuTong); 
  nanDuMenu.add(cKunNan); 
  fenShuMenu.add(fenShuItem); 
  youXiMenu.add(kaiShiYouXi); 
  youXiMenu.add(exitItem); 
  menuBar.add(youXiMenu); 
  menuBar.add(nanDuMenu); 
  menuBar.add(fenShuMenu); 
  menuBar.add(guanYuMenu); 
//--------------�˵������ý���------------------//
//--------------����¼�������ݼ�------------------//
  zuoZheItem.addActionListener(this); //�����������Ӳ˵���Ӽ�����
  kaiShiYouXi.addActionListener(this); //�����ڿ�ʼ��Ϸ�Ӳ˵���Ӽ�����
  exitItem.addActionListener(this); //��������Ϸ�Ӳ˵���Ӽ�����
  addKeyListener(this); //��Ӽ��̼���
  fenShuItem.addActionListener(this); //����߷����Ӳ˵���Ӽ�����
  KeyStroke keyOpen = KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK); //����KeyStroke��һ������ʵ��S+Ctrl
  kaiShiYouXi.setAccelerator(keyOpen); //���������ʵ��S+Ctrl��Ϊ��ʼ��Ϸ�Ӳ˵��Ŀ�ݼ�
  KeyStroke keyExit = KeyStroke.getKeyStroke('B',InputEvent.CTRL_DOWN_MASK); //����KeyStroke��һ������ʵ��B+Ctrl
  exitItem.setAccelerator(keyExit);  //���������ʵ��B+Ctrl��Ϊ�˳���Ϸ�Ӳ˵��Ŀ�ݼ�
//--------------����¼�������ݼ�����------------------//
//--------------���ô�������------------------//
  setJMenuBar(menuBar); //��menuBar�˵�����ӵ�JFrame����
  setTitle("̰����"); //����JFrame��������Ϊ̰����
  setResizable(false); //����JFrame����������
  setBounds(400,400,600,600); //���ô���λ�����꣨400��400������С��600��600��
  validate(); //��֤�������
  setDefaultCloseOperation(EXIT_ON_CLOSE); //�رշ�ʽ�˳����ر�
//--------------���ô������Խ���------------------//
} 
 
public static void main(String args[]) { 
   new Snake(); 
} 
/**
 * �����¼�ʵ�ֵ�ActionListener�ӿ�
 */
public void actionPerformed(ActionEvent e){ 
    //�����ʼ��Ϸ���߳�Ҫ����
  if(e.getSource()==kaiShiYouXi) 
  { 
   length = 6; 
   life = 0; 
   setFoods(0); 
   if(she==null) 
   { 
    she=new Thread(this); 
    she.start(); 
   } 
   else if(she!=null) 
   { 
    she=null; 
    she= new Thread(this); 
    she.start(); 
   } 
  } 
  if(e.getSource()==exitItem) 
  { 
   System.exit(0); 
  } 
  if(e.getSource()==zuoZheItem) 
  { 
   JOptionPane.showMessageDialog(this, "����"+"\n\n"); 
  } 
  if(e.getSource()==fenShuItem) 
  { 
      JOptionPane.showMessageDialog(this,"��߼�¼Ϊ"+fenshu+"");  
  } 
  
} 
/**
 * ������Ƿ��������Ե�ʳ�����Ϊ�������������ˢ��
 */
public void check(){ 
  isDead(); 
  if(she!=null) 
  { 
   if(growth==0) 
   { 
    reform(); //�õ�ʳ�� ����
   } 
   else 
   { 
    upgrowth(); //����ʳ�� 
   } 
   if(x==objectX&&y==objectY) 
   { 
    object=0; //�������ʳ��ͱ����˾�û����
    growth=1; 
    toolkit.beep(); //����������
   } 
   if(object==0) 
   { 
    object=1; 
    objectX=(int)Math.floor(Math.random()*39)*10; 
    objectY=(int)Math.floor(Math.random()*29)*10+50; 
   } 
   this.repaint(); //�ػ� 
  } 
} 
/**
 * �ж����Ƿ����������˾ͽ����߳���Ϊnull
 */
void isDead() 
{ 
  //�ж���Ϸ�Ƿ�����ķ��� 
  if(z==4) 
  { 
   x=x+10; 
  } 
  else if(z==3) 
     { 
        x=x-10; 
     } 
  else if(z==2) 
     { 
        y=y+10; 
     } 
  else if(z==1) 
     { 
        y=y-10; 
     } 
  //�ж��Ƿ���ǽ
  if(x<0||x>590||y<50||y>590) 
  { 
   she=null; 
  } 
  //�ж��Ƿ������Լ�
  for(i=1;i<length;i++) 
  { 
   if(m[i]==x&&n[i]==y) 
   { 
    she=null; 
   } 
  } 
   
} 
/**
 * �Ե��������length,growth,time,fuenshu,life,foods���ݵĸ���
 */
public void upgrowth() 
{  
  //���߳Ե�����ʱ�ķ��� 
  if(length<50) 
  { 
   length++; 
  }  
  growth--; 
  time=time-10; 
  reform(); 
  life+=100; 
  if(fenshu<life) 
  { 
   fenshu = life; 
  } 
  setFoods(getFoods() + 1); 
} 
/**
 * ����ָ���ķ���zͨ������m[i]n[i]ÿ���ڵ��λ������
 */
public void reform() 
{ 
  for(i=length-1;i>0;i--) 
  { 
   
   m[i]=m[i-1]; 
   n[i]=n[i-1]; 
  } 
  if(z==4) 
  { 
   m[0]=m[0]+10; 
  } 
  if(z==3) 
  { 
   m[0]=m[0]-10; 
  } 
  if(z==2) 
  { 
   n[0]=n[0]+10; 
  } 
  if(z==1) 
  { 
   n[0]=n[0]-10; 
  } 
} 

public void keyPressed(KeyEvent e) 
{ 
  if(she!=null) 
  { 
   if(e.getKeyCode()==KeyEvent.VK_W) 
   { 
    if(z!=2) 
    { 
     z=1; 
     check(); 
    } 
   } 
   else if(e.getKeyCode()==KeyEvent.VK_S) 
   { 
    if(z!=1) 
    { 
     z=2; 
     check(); 
    } 
   } 
   else if(e.getKeyCode()==KeyEvent.VK_A) 
   { 
    if(z!=4) 
    { 
     z=3; 
     check(); 
    } 
   } 
   else if(e.getKeyCode()==KeyEvent.VK_D) 
   { 
    if(z!=3) 
    { 
     z=4; 
     check(); 
    } 
   } 
  
  } 
  
} 
public void keyReleased(KeyEvent e) 
{ 
  
} 
public void keyTyped(KeyEvent e) 
{ 
  
} 
/**
 * ��ͼ��ͼ��ʳ��ͼ����ͼ
 * ����Ҳ����дWindows�л�ͼ
 * ����ʱ����ͨ��repaint�ظ���ͼ
 */
public void paint(Graphics g)  { 
  g.setColor(Color.darkGray); //���ñ��� 
  g.fillRect(0,50,600,600); //���û��巶Χ
  g.setColor(Color.green); //����ɫ
  for(i=0;i<=length-1;i++) 
  { 
   g.fillRect(m[i],n[i],10,10); 
  } 
  g.setColor(Color.pink); //�ߵ�ʳ�� 
  g.fillRect(objectX,objectY,10,10); 
  g.setColor(Color.white); 
  g.drawString("��ǰ����"+this.life,440,60); 
  //g.drawString("��ǰ�ѳ�ʳ����"+this.foods,6,72); 
}
public int getFoods() {
    return foods;
}
public void setFoods(int foods) {
    this.foods = foods;
}
}
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
     * 菜单栏
     */
    private JMenuBar menuBar; 
    /**
     * 游戏，难度，分数，关于菜单栏
     */
    private JMenu youXiMenu,nanDuMenu,fenShuMenu,guanYuMenu; 
    /**
     * 开始游戏，结束游戏，关于作者，最高分数
     */
    private JMenuItem kaiShiYouXi,exitItem,zuoZheItem,fenShuItem; 
    /**
     * 困难程度中的子菜单
     */
    private JCheckBoxMenuItem cJianDan,cPuTong,cKunNan; 
    /**
     * 蛇长度
     */
    private int length = 10; 
    private Toolkit toolkit; //工具箱作用就是发出声音
    /**
     * x:蛇头距离x轴距离
     * y:蛇头距离y轴距离
     * z:蛇前进方向：4:->;3:<-;2:下；1：上
     * object，objectX，objectY:食物对象个数随机生成的食物坐标
     * growth：长大一次0：没有，1:有
     * time:走一步的时间
     */
    private int i,x,y,z,objectX,objectY,object=0,growth=0,time;
/**
 * 记录蛇身每个节点坐标位置
 */
    private int m[]=new int[50]; 
    private int n[]=new int[50]; 
    /**
     * 蛇对象
     */
    private Thread she = null;
    /**
     * 吃到的食物数量
     */
    private int foods = 0; 
    /**
     * 当前分数
     */
    private int life=0; 

    /**
     * 最高分数
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
 * 窗体属性菜单栏设置
 */
public Snake() { 
 //-----------菜单栏设置--------------------//
  setVisible(true); 
  menuBar = new JMenuBar(); 
  toolkit=getToolkit(); 
  youXiMenu = new JMenu("游戏"); 
  kaiShiYouXi = new JMenuItem("开始游戏"); 
  exitItem = new JMenuItem("退出游戏"); 
  nanDuMenu = new JMenu("困难程度"); 
  cJianDan = new JCheckBoxMenuItem("简单"); 
  cPuTong = new JCheckBoxMenuItem("普通"); 
  cKunNan = new JCheckBoxMenuItem("困难"); 
  fenShuMenu = new JMenu("积分排行"); 
  fenShuItem = new JMenuItem("最高记录"); 
  guanYuMenu = new JMenu("关于"); 
  zuoZheItem = new JMenuItem("关于作者"); 
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
//--------------菜单栏设置结束------------------//
//--------------添加事件监听快捷键------------------//
  zuoZheItem.addActionListener(this); //给关于作者子菜单添加监听器
  kaiShiYouXi.addActionListener(this); //给关于开始游戏子菜单添加监听器
  exitItem.addActionListener(this); //给结束游戏子菜单添加监听器
  addKeyListener(this); //添加键盘监听
  fenShuItem.addActionListener(this); //给最高分数子菜单添加监听器
  KeyStroke keyOpen = KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK); //返回KeyStroke的一个共享实例S+Ctrl
  kaiShiYouXi.setAccelerator(keyOpen); //将这个共享实例S+Ctrl作为开始游戏子菜单的快捷键
  KeyStroke keyExit = KeyStroke.getKeyStroke('B',InputEvent.CTRL_DOWN_MASK); //返回KeyStroke的一个共享实例B+Ctrl
  exitItem.setAccelerator(keyExit);  //将这个共享实例B+Ctrl作为退出游戏子菜单的快捷键
//--------------添加事件监听快捷键结束------------------//
//--------------设置窗体属性------------------//
  setJMenuBar(menuBar); //将menuBar菜单栏添加到JFrame容器
  setTitle("贪吃蛇"); //设置JFrame容器标题为贪吃蛇
  setResizable(false); //设置JFrame容器不缩放
  setBounds(400,400,600,600); //设置窗体位置坐标（400，400），大小（600，600）
  validate(); //验证这个容器
  setDefaultCloseOperation(EXIT_ON_CLOSE); //关闭方式退出即关闭
//--------------设置窗体属性结束------------------//
} 
 
public static void main(String args[]) { 
   new Snake(); 
} 
/**
 * 触发事件实现的ActionListener接口
 */
public void actionPerformed(ActionEvent e){ 
    //如果开始游戏蛇线程要开启
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
   JOptionPane.showMessageDialog(this, "刘博"+"\n\n"); 
  } 
  if(e.getSource()==fenShuItem) 
  { 
      JOptionPane.showMessageDialog(this,"最高记录为"+fenshu+"");  
  } 
  
} 
/**
 * 检查蛇是否死处理，吃到食物后行为，处理完后容器刷新
 */
public void check(){ 
  isDead(); 
  if(she!=null) 
  { 
   if(growth==0) 
   { 
    reform(); //得到食物 过程
   } 
   else 
   { 
    upgrowth(); //生成食物 
   } 
   if(x==objectX&&y==objectY) 
   { 
    object=0; //到这个点食物就被吃了就没有了
    growth=1; 
    toolkit.beep(); //发出笔声音
   } 
   if(object==0) 
   { 
    object=1; 
    objectX=(int)Math.floor(Math.random()*39)*10; 
    objectY=(int)Math.floor(Math.random()*29)*10+50; 
   } 
   this.repaint(); //重绘 
  } 
} 
/**
 * 判断蛇是否已死，死了就讲蛇线程设为null
 */
void isDead() 
{ 
  //判断游戏是否结束的方法 
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
  //判断是否碰墙
  if(x<0||x>590||y<50||y>590) 
  { 
   she=null; 
  } 
  //判断是否碰到自己
  for(i=1;i<length;i++) 
  { 
   if(m[i]==x&&n[i]==y) 
   { 
    she=null; 
   } 
  } 
   
} 
/**
 * 吃到东西后对length,growth,time,fuenshu,life,foods数据的更新
 */
public void upgrowth() 
{  
  //当蛇吃到东西时的方法 
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
 * 按着指定的方向z通过设置m[i]n[i]每个节点的位置行走
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
 * 画图蛇图，食物图，地图
 * 这里也是重写Windows中画图
 * 调用时可以通过repaint重复绘图
 */
public void paint(Graphics g)  { 
  g.setColor(Color.darkGray); //设置背景 
  g.fillRect(0,50,600,600); //设置画板范围
  g.setColor(Color.green); //蛇颜色
  for(i=0;i<=length-1;i++) 
  { 
   g.fillRect(m[i],n[i],10,10); 
  } 
  g.setColor(Color.pink); //蛇的食物 
  g.fillRect(objectX,objectY,10,10); 
  g.setColor(Color.white); 
  g.drawString("当前分数"+this.life,440,60); 
  //g.drawString("当前已吃食物数"+this.foods,6,72); 
}
public int getFoods() {
    return foods;
}
public void setFoods(int foods) {
    this.foods = foods;
}
}
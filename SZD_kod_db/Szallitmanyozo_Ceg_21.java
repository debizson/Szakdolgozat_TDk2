//Szakdolgozat   Dinnyes Balazs Emil Programozo matematikus DIBMAAT.SZE kelt. 2010 05. 06.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.text.*;

import BranchABound.BranchAndBound;
import BranchABound.BranchAndBoundController;

import java.lang.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;

class	JavaFilter extends FileFilter
{
	public boolean accept(File f)
	{
		return f.getName().toUpperCase().endsWith(".TXT") || f.isDirectory();
	}

	public String getDescription()
	{
		return "txt-allomamyok";
	}
}//end JavaFilter

class KepGif extends JPanel
{
	private Image img;
	private String fileName;
	private MediaTracker tr;
	public KepGif(String fileName)
	{
		img= Toolkit.getDefaultToolkit().createImage(fileName);
		tr=new MediaTracker(this);
		tr.addImage(img,0);
		try
		{
			tr.waitForID(0);
		}
		catch (InterruptedException ex)
		{
		}
		finally
		{
			tr.removeImage(img,0);
		}
	}
	protected void paintComponent(Graphics gr)
	{
		super.paintComponent(gr);
		gr.drawImage(img,0,0,50,30,this);
	}
}

class TSPopt extends Thread{


	int j;
	int n;
	ListIterator iter0;
	ListIterator iter1;
	int[][] kapcsolatitomb;
	int lengh;
	int [] kulcs;
	String strcsucsok,strcsucsokord;
	int csucsmegjegyezkezdo;
	LinkedList<Object> list0 = new LinkedList<Object>();
	LinkedList <Object> list1 = new LinkedList<Object>();
	int partuthossz;
	String name1;
	int kombLevagott;

	public TSPopt(int [] [] kapcsolatitomb,int n,String name1)

	//kapcsolatitomb: a kapcsolati matrix, n: varosok szama, name1: ha file-bol megnyitott adatokkal dolgozik a program, a file neve, amit tovabbad a grafikus megjelenitesnek
	{
		this.kapcsolatitomb=kapcsolatitomb;
		this.n=n;
		this.name1=name1;
		kulcs=new int[n];
		strcsucsok="";strcsucsokord="";
		//System.out.println(kapcsolatitomb[1][0]);
	}//end TSPopt

	public void dinkiscsucsmake(String strcsucsok)
	{
		int csucsmegjegyez=csucsmegjegyezkezdo;
		do
		{
			StringTokenizer st = new StringTokenizer(strcsucsok," ");
			boolean bool=false;
			if( csucsmegjegyez>=n)
			{
				csucsmegjegyezkezdo=n+1;
				return;
			}
			while(st.hasMoreTokens())
				{
					String sh=st.nextToken();
					String string=String.valueOf(sh);
					int kezdointcsucs=Integer.parseInt(string);
					if(kezdointcsucs==csucsmegjegyez)
					{
						 csucsmegjegyez++;bool=true;
					}
					if((kezdointcsucs==n-1) && (csucsmegjegyez==n))
						 return;
				 }
				 if(!bool)break;
				 	else if(csucsmegjegyez==n)break;
			}while(true );

			String past=String.valueOf(csucsmegjegyez);
			strcsucsok=strcsucsok.concat(" "+past+"   ");
			csucsmegjegyezkezdo=csucsmegjegyez;
			//System.out.println(strcsucsok);
			rendez(strcsucsok,past);
	}//end dinkiscsucsmake()
public void dinkiscsucspast(String strcsucsok,String strcsucsokord)
	{
				list1.addLast(strcsucsokord);
				list1.addLast(new Integer(partuthossz));
				list1.addLast(strcsucsok);
	}//end dinkiscsucspast


	public void cserel(int i,int k,int[]kulcs)
	{
		if(i<0 || i>=lengh || k<0 || k>=lengh || i==k) return;
			int l=kulcs[i];
			kulcs[i]=kulcs[k];
			kulcs[k]=l;
	}//end cserel

	public void legkisebb(int i,int[]kulcs)
	{
		if(i<0 || i>=lengh)
		{
			 return;
		}
		for(int k=i+1;k<lengh;k++) {
			if(kulcs[i]>kulcs[k]) cserel(i,k,kulcs);
		}
		legkisebb(i+1,kulcs);
	}//end legkisebb

	public void rendez(String strcsucsok,String utolso)
	{

		int u=0;
		StringTokenizer stv = new StringTokenizer(strcsucsok," ");
			while(stv.hasMoreTokens())
			{
				kulcs[u]=Integer.valueOf(stv.nextToken());
				u++;
			}
		legkisebb(0,kulcs);
		String strcsucsokord="";
		for(int i=0;i<lengh;i++)
	    	strcsucsokord=strcsucsokord.concat(String.valueOf(kulcs[i])+" ");
		strcsucsokord=strcsucsokord.concat(utolso);
		reszuthossz(strcsucsok,strcsucsokord);
	}//end rendez

	public void reszuthossz(String strcsucsok,String strcsucsokord)
	{
	 	StringTokenizer stz = new StringTokenizer(strcsucsok," ");
		int strtotomb1=Integer.valueOf(stz.nextToken());
	  	partuthossz=kapcsolatitomb[0][strtotomb1];
		while(stz.hasMoreTokens())
		{
		   	int strtotomb2=strtotomb1;
		  	strtotomb1=Integer.valueOf(stz.nextToken());
	  		partuthossz+=kapcsolatitomb[strtotomb2][strtotomb1];
		}
		//System.out.println(strcsucsokord);
		//System.out.println(strcsucsok);
		levagas(strcsucsok,strcsucsokord);
	}//end reszuthossz

	public void elsoszintmake()
		{
			for(int i=1;i<n;i++)
			{
			   list0.add(String.valueOf(i));
			   list0.add(kapcsolatitomb[0][i]);
			   list0.add(String.valueOf(i));

			   j=0;
			}
	}

	public void levagas(String strcsucsok,String strcsucsokord)
	{
		if(j==n-1)
		{
			dinkiscsucspast(strcsucsok,strcsucsokord);
		 	return;
		}
		//if(list1.size()<=3)
		//{
		//   	dinkiscsucspast(strcsucsok,strcsucsokord);
		//	return;
		//}
		int index;
		index=list1.indexOf(strcsucsokord);
		if(index!=-1)
		{
			if(((Integer)list1.get(index+1))>=partuthossz)
			{
				list1.remove(index);
				list1.remove(index);
				list1.remove(index);
				kombLevagott++;
			}
				else
				return;
		}
		//System.out.println(strcsucsok);
		dinkiscsucspast(strcsucsok,strcsucsokord);
	}//end levagas

	public void KiScSuCsok(String strcsucsok)
	{
		csucsmegjegyezkezdo=1;
		while(csucsmegjegyezkezdo<n)
		{
			iter1=list1.listIterator();
			dinkiscsucsmake(strcsucsok);
			csucsmegjegyezkezdo++;
		}

	}//end KiScSuCsok(


	public void NAgYCSuCSoK(int lenght)
	{
		iter0=list0.listIterator();
		while(iter0.hasNext())
		{
			iter0.next();
			iter0.next();
			String strcsucsok=(String)iter0.next();
			lengh=lenght;
			KiScSuCsok(strcsucsok);
		}
	}//end NAgYCSuCSoK

	public int vegsoreszuthossz(String strstrst)
	{
		StringTokenizer stze = new StringTokenizer(strstrst," ");
		int strtotomb1=Integer.valueOf(stze.nextToken());
	   	partuthossz=kapcsolatitomb[0][strtotomb1];
		while(stze.hasMoreTokens())
		{
			int strtotomb2=strtotomb1;
		    strtotomb1=Integer.valueOf(stze.nextToken());
	  		partuthossz+=kapcsolatitomb[strtotomb2][strtotomb1];
		}
		return partuthossz;
	}//end vegsoreszuthossz


	public void minimumNodesofEdges(int [] minimumEdgesArray){

		for(int i = 0; i < n; i++){
			int x = 2000000000;
			int y = 0;
			for (int j = 0; j < n; j++){
				y = kapcsolatitomb[i][j];
				if(x > y){
					x = y;
				}	
			} 
			minimumEdgesArray[i] = x;

		}

	}

	public void run()
	{
		

		int [] minimumEdgesOfNodesArray = new int [n];   // removalbel line if don't want to use Branch and Bound

		minimumNodesofEdges(minimumEdgesOfNodesArray);	// removalbel line if don't want to use Branch and Bound

		BranchAndBoundController BBObj = new BranchAndBoundController(list0, n, kapcsolatitomb, minimumEdgesOfNodesArray);   // removalbel line if don't want to use Branch and Bound

		elsoszintmake();

		BBObj.runCurrentLevel(j);		// removalbel line if don't want to use Branch and Bound
		//System.out.println("városok száma: " + n);
		kombLevagott = 0;
		for(j=1;j<n-1;j++)															//********** */
		{

			NAgYCSuCSoK(j);

			list0.clear();
			list0.addAll(list1);

			//if(j>1)
			BBObj.runCurrentLevel(j);		// removalbel line if don't want to use Branch and Bound

			list1.clear();

			System.out.println(" A kombinatirikusan levágott csúcsok a " + (j+2) + ". szinten: " + kombLevagott   + "db nodes.");
		}
		ListIterator iterresult= list0.listIterator();
		while(iterresult.hasNext())
		{
			iterresult.next();
			iterresult.next();
			String strstr;
			strstr=(String)iterresult.next();
			strstr=strstr+"0";
			list1.add(new Integer(vegsoreszuthossz(strstr)));
			list1.add(strstr);
		}
		ListIterator iterresult1= list1.listIterator();
		int numas=200000000;
		String strvegsomego="";
		while(iterresult1.hasNext())
		{
			int szama=(Integer)iterresult1.next();
			if(numas>szama)
			{
				numas=szama;
				strvegsomego=(String)iterresult1.next();
			}
			else
				iterresult1.next();
		}
		strvegsomego="0 "+strvegsomego;
		//System.out.println("strvegsomego: " + strvegsomego );
		new TSPGraphic(strvegsomego,n,kapcsolatitomb,name1);
		name1="";
		// strvegsomego: a legrovidebb korut a varosok sorszamaival, n: varosok szama, kapcsolatitomb: a kapcsolati matrix, name1: ha file-bol megnyitott adatokkal dolgozik a program, a file neve, amit tovabbad a grafikus megjelenitesnek
	}//end SZINTEK

}//class TSPopt

//******************



class TSPGraphic extends JFrame
{

	private String graphicTSPmego;
	private int intvaros;
	private int [][]kapcsolatiarray;
	private Container cp= getContentPane();
	static int cim=1;
	private String nev1="";

   	public TSPGraphic(String graphicTSPmego,int intvaros,int[][]kapcsolatiarray,String nev1)
   	{
  		this.graphicTSPmego=graphicTSPmego;
   	 	this.intvaros=intvaros;
   	 	this.nev1=nev1;
  		setDefaultCloseOperation(HIDE_ON_CLOSE);
   		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   		setBounds(screenSize.width/3,0,screenSize.width/3*2, screenSize.height);
   	   	setTitle((cim++)+". TSP Graphic "+nev1);
   	 	JPanel Panelmid = new JPanel();
   	 	StringTokenizer stgg = new StringTokenizer(graphicTSPmego," ");
		int s=1;
		JLabel Jlabl=new JLabel(intvaros+" varos, legrovideb korut:  ");
		Jlabl.setForeground(Color.RED);
		while(stgg.hasMoreTokens())
		{
			int res= Integer.valueOf(stgg.nextToken());
			if(s==intvaros+1)
				Jlabl.setText(Jlabl.getText()+(res+1));
			else
				Jlabl.setText(Jlabl.getText()+(res+1)+"  ->   ");
			s++;
		}
		StringTokenizer stgo = new StringTokenizer(graphicTSPmego," ");
		s=1;
		JLabel Jlab=new JLabel("");
		Jlab.setForeground(Color.BLACK);
		int kez1 =Integer.valueOf(stgo.nextToken());
		int osszg=0;
		while(stgo.hasMoreTokens())
		{
			String atalaStr=stgo.nextToken();
			int kez2= Integer.valueOf(atalaStr);
			String strr=String.valueOf(kapcsolatiarray[kez1][kez2]);
			osszg=osszg+kapcsolatiarray[kez1][kez2];
		 	if(s==intvaros)
		 	{
				Jlab.setText(Jlab.getText()+kapcsolatiarray[kez1][kez2]);
			}
			else
				Jlab.setText(Jlab.getText()+kapcsolatiarray[kez1][kez2]+"+");
				s++;
				kez1=kez2;
		}
		//System.out.println(kapcsolatiarray);
		Jlab.setText(Jlab.getText()+"  =  "+osszg+"km");
		Panelmid.setLayout(new GridLayout(2,1));
		Panelmid.add(Jlabl);
		Panelmid.add(Jlab);
		cp.setLayout(new GridLayout(3,1));
    	 	cp.add(new TSPLabelPanel(kapcsolatiarray,intvaros,graphicTSPmego));
    	 	add(Panelmid);
    	  	cp.add(new PolygonPanel(graphicTSPmego,intvaros));
    		show();
      }
 }//class TSPGraphic

//*************

class TSPLabelPanel extends JPanel
{
	private int[][] Tomb;
	private int n;
	private JLabel Jlabl;
	public	TSPLabelPanel(int[][]Tomb,int n,String mego)
	{
		this.Tomb=Tomb;
		this.n=n;
		setLayout(new GridLayout(n+2,(n/2*n)));
		for(int sor=1; sor<=n;sor++)
		{
	    	for(int oszlop=sor+1;oszlop<=n;oszlop++)
	      	{
				add(Jlabl=new JLabel(""));
				StringTokenizer stgh = new StringTokenizer(mego," ");
				int g0int = Integer.valueOf(stgh.nextToken());
				int gint=0;
				while(stgh.hasMoreTokens())
				{
				   	gint=Integer.valueOf(stgh.nextToken());
				   	if((sor-1==g0int && oszlop-1==gint) || (oszlop-1==g0int && sor-1==gint))
				   	{
						Jlabl.setForeground(Color.RED);
				   		break;
					}
				   	else if(sor%2==1)
				   		Jlabl.setForeground(new Color(100,100,255));
				   	else
				   		Jlabl.setForeground(Color.BLACK);
				   	g0int=gint;
				}
				Jlabl.setText(Jlabl.getText()+sor+" -> "+oszlop+": "+Tomb[sor-1][oszlop-1]+"   ");
			}
		}
	}
}//class TSPLabelPanel

//************

class PolygonPanel extends JPanel
{
	private int n;
	private int radius;
	private	String graphicTSPmego2;
	public PolygonPanel(String graphicTSPmego2,int n)
	{
		this.graphicTSPmego2=graphicTSPmego2;
		this.n=n;
	}
	protected void paintComponent(Graphics gr)
	{
		super.paintComponent(gr);
		gr.translate(getWidth()/2,getHeight()/2);
		radius=getWidth()/2/12*2;
		int alfa=360/n;
		for(int j=0; j<n;j++)
		{
			int x1=(int)(radius*Math.cos(Math.toRadians(j*alfa)));
			int y1=(int)(radius*Math.sin(Math.toRadians(j*alfa)));
			int StcsucsX=(int)((radius+15)*Math.cos(Math.toRadians(j*alfa)));
			int StcsucsY=(int)((radius+15)*Math.sin(Math.toRadians(j*alfa)));
			String csucsi=String.valueOf((char)('a'+j));
			gr.setColor(Color.BLACK);
			gr.setFont(new Font("Arial",Font.BOLD,15));
			gr.drawString(csucsi,StcsucsX,StcsucsY);
			for(int i=j;i<n;i++)
			{

				int x=(int)(radius*Math.cos(Math.toRadians(i*alfa)));
				int y=(int)(radius*Math.sin(Math.toRadians(i*alfa)));
				StringTokenizer stgh = new StringTokenizer(graphicTSPmego2," ");
				int g0int = Integer.valueOf(stgh.nextToken());
				int gint=0;
				boolean booleg=false;
				while(stgh.hasMoreTokens())
				{
					gint=Integer.valueOf(stgh.nextToken());
					if((j==g0int && i==gint) || (i==g0int && j==gint))
					{
						gr.setColor(new Color(255,0,0));
						gr.drawLine(x1,y1,x,y);
						booleg=true;
						break;
					}
					g0int=gint;
				}
				if(booleg==false)
				{
					gr.setColor(Color.DARK_GRAY);
					gr.drawLine(x1,y1,x,y);
				}

			}
		}
	}
}//class PolygonPanel

//************

class JTextFieldLimit extends PlainDocument
{
	private int limit;
	private boolean toUppercase = false;
	JTextFieldLimit(int limit)
	{
		super();
		this.limit = limit;
	}
	JTextFieldLimit(int limit, boolean upper)
	{
    	super();
    	this.limit = limit;
    	toUppercase = upper;
	}

   public void insertString
    (int offset, String  strcsucsok, AttributeSet attr)throws BadLocationException
    {
   		if(strcsucsok == null) return;
		if((getLength() + strcsucsok.length()) <= limit)
		{
    		if(toUppercase) strcsucsok = strcsucsok.toUpperCase();
      		super.insertString(offset, strcsucsok, attr);
      	}
    }
}//class JTextFieldLimit

//************

class HaromPanel extends JPanel implements ActionListener, FocusListener
{
      JPanel Panel1 = new JPanel();
      JPanel Panel2 = new JPanel();
      JPanel Panel3 = new JPanel();

	//Panel1 komponensei :
    private JTextArea lbInfo1;
	JTextField tfvn;
	final int vn=101;
	JTextField [][] tfAdatok = new JTextField[vn][vn] ;
    JLabel  jlFigy;
    int vint;
    int varossz;
    JButton btEredmeny;
    int[][]utakTomb   = new int[vn][vn];
    JTextArea  uzenet;
    JButton btOk;
    KepGif kepecske1;
    Random generator = new Random();
    JLabel [][] tfJLab=new JLabel[vn][vn];
	JButton bt1open;
	JButton bt1save;
	private JFileChooser fco1 = new JFileChooser();
	boolean b1Open=false;
	String [][]tomBopen=new String[vn][vn];
	JPanel mentok1=new JPanel();
	private JFileChooser fcs1 = new JFileChooser();
	String name1;
	int varossz2;
	JScrollPane scrollbar1;
	int lost=0;
	boolean ugras=false;
	Point pontmemory=new Point(0,0);
	Thread TSPopti;
	Color focuslost;
	/****************/
	//Panel2 komponensei :
	final int db=16;
	JLabel jlRakasNum;
	JTextField tfRakasN;
	JButton btTorol;
	JTextArea uzenet2;
	JButton btEredmeny2;
	int vint2;
	JButton bt2open;
	JButton bt2save;
	JLabel jlFigy2;
	KepGif kepecske2;
	long [] fileTomb= new long[db];
	JLabel [] tfJLab2=new JLabel[db];
	JTextField [] tfAdatok2 = new JTextField[db];
	int ladaNum;
	int rakasNum;
	JPanel mentokL=new JPanel();
	private JFileChooser fco2 = new JFileChooser();
	private JFileChooser fcs2 = new JFileChooser();
	boolean boolte;
	//int ladaNum2;
	int rnum=15;
	JScrollPane scrollbar2;
	/****************/

	//Panel3 komponensei :
	final int db3=21;
	JTextArea uzenet3;
	JButton btEredmeny3;
	int vint3;
	JLabel jl100;
	JLabel jlFigy3;
	JButton bt3open;
	JButton bt3save;
	KepGif kepecske3;
	long [] targyTomb= new long[db3];
	JLabel [] tfJLab3=new JLabel[db3];
	JTextField [] tfAdatok3 = new JTextField[db3] ;
	int [] tombA = new int [db3];
	JPanel mentokT=new JPanel();
	private JFileChooser fco3 = new JFileChooser();
	private JFileChooser fcs3 = new JFileChooser();
	boolean boolt;
	JButton btTorol2;
	final int n20=20;

	public HaromPanel()
	{
		setBackground(Color.BLUE);

	    setLayout(new GridLayout(3,1,10,10));
	    scrollbar1 = new JScrollPane(Panel1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    add(scrollbar1);
		add(Panel2);
    	add(Panel3);
		/*********************/
		Panel1.setLayout(new FlowLayout());
		JTextArea lbInfo1 = new JTextArea("Udvozollek a szakdolgozatom programjaban. Ebben a reszben megkeresheted(TSP) - a varosok kozt levo utak hosszaval megadva - a minden varost erinto legrovidebb korutat. Az 'a'(1) varos a start es a cel hely. Ez a program nagy segitseg egy szallitmanyozo vallalat szamara. Hasznalja egezseggel!    A VAROSOK SZAMA MEGADASA UTAN ENTER-t v. OK NYOMJON!!! A VAROS HOSSZADATAI UTAN PEDIG RESULT!!!",8,27);
		lbInfo1.setBackground(new Color(250,221,182));
		lbInfo1.setLineWrap(true);
		lbInfo1.setWrapStyleWord(true);
		lbInfo1.setEditable(false);
		mentok1.setLayout(new GridLayout(3,1,5,5));
		mentok1.add(kepecske1=new KepGif("fold2.gif"));
		mentok1.add(bt1open=new JButton("Open"));
		mentok1.add(bt1save=new JButton("Save"));
		Panel1.add(mentok1);
		Panel1.add(lbInfo1);
		Panel1.add(new JLabel("varosok szama(max.100):"));
		Panel1.add(tfvn = new JTextField(3));
		tfvn.setDocument(new JTextFieldLimit(3));
		Panel1.add(btOk=new JButton("OK"));
		Panel1.add(btEredmeny=new JButton("Result"));
		btEredmeny.addActionListener(this);
		btEredmeny.setEnabled(false);
		tfvn.addActionListener(this);
		btOk.addActionListener(this);
		bt1open.addActionListener(this);
		bt1save.addActionListener(this);
		bt1save.setEnabled(false);
		Panel1.add(jlFigy = new JLabel(""));
		Panel1.add(uzenet=new JTextArea("",1,18));
		uzenet.setForeground(new Color(0,0,255));
		uzenet.setEditable(false);
		uzenet.setLineWrap(true);
		uzenet.setWrapStyleWord(true);
		jlFigy.setForeground(new Color(255,0,0));
		fco1.setCurrentDirectory(new File("."));
		fco1.setFileFilter(new JavaFilter());
		fcs1.setCurrentDirectory(new File("."));
		fcs1.setFileFilter(new JavaFilter());

		//end konstruktor Panel1

		/*************************************/

		//Panel2 konstruktor DEKLARACIOI :

		mentokL.setLayout(new GridLayout(3,1,5,5));
		mentokL.add(kepecske2=new KepGif("doboz2.gif"));
		mentokL.add(bt2open=new JButton("Open"));
	    mentokL.add(bt2save=new JButton("Save"));
		Panel2.add(mentokL);
	    JTextArea lbInfo2 = new JTextArea("Egy kontener raktarban egy sorban egymas mellett tarolnak n darab kontenert. A raktaros at akarja rendezni a kontenereket ugy, hogy nehanyat egymasra rak. Az atrendezest bizonyos kontenerek egyesevel torteno atrakasaval lehet vegezni. Ha az atrendezes soran az i-edik kontenerhelyen levo kontenert a j-edik kontenerhelyre rakja at, ennek koltsege si|i - j|, ha a kontener sulya si. A raktaros olyan atrendezest akar, amely utan pontosan k kontenerhelyen lesz kontener (esetleg egymasra rakva). A raktaros az optimalis atrendezest keresi, tehat amelyre az osszkoltseg minimalis. Ez a panel kiszamitja a raktar optimalis atrendezesenek koltseget, es a rakasok HELYEIT is megmutatja! A PARAMETER BEVITELE UTAN ADJA MEG A LADAK SULYAT, UTANNA A RESULT GOMBOT KELL MEGNYOMNI!! ",8,54);
	   	lbInfo2.setBackground(new Color(250,221,182));
	   	lbInfo2.setLineWrap(true);
	   	lbInfo2.setWrapStyleWord(true);
	   	lbInfo2.setEditable(false);
		Panel2.add(lbInfo2);
		Panel2.add(jlRakasNum=new JLabel("Az keletkezett lefoglalt rakas helyek:(Max 15):"));
	    Panel2.add(tfRakasN=new JTextField(3));
	    tfRakasN.setDocument(new JTextFieldLimit(2));
	    Panel2.add(btTorol = new JButton("Torol"));
	    Panel2.add(btEredmeny2=new JButton("Result"));
	    Panel2.add(jlFigy2 = new JLabel(""));
	    Panel2.add(uzenet2=new JTextArea("",2,36));
	    jlFigy2.setForeground(new Color(255,0,0));
	    uzenet2.setForeground(new Color(0,0,255));
		uzenet2.setEditable(false);
		uzenet2.setLineWrap(true);
		uzenet2.setWrapStyleWord(true);
		btEredmeny2.addActionListener(this);
		tfRakasN.addActionListener(this);
		tfRakasN.addFocusListener(this);
		bt2open.addActionListener(this);
		btTorol.addActionListener(this);
		bt2save.addActionListener(this);
		fco2.setCurrentDirectory(new File("."));
		fco2.setFileFilter(new JavaFilter());
		fcs2.setCurrentDirectory(new File("."));
		fcs2.setFileFilter(new JavaFilter());
		mezok2(rnum);
		for(int i=1; i<=rnum;i++)
		{
			tfAdatok2[i].addActionListener(this);
			tfAdatok2[i].addFocusListener(this);
		}
		//end konstruktor Panel1

		//*****************************************

		//Panel3 konstruktor DEKLARACIOI :
		mentokT.setLayout(new GridLayout(3,1,5,5));
		mentokT.add(kepecske3=new KepGif("vaza.gif"));
		mentokT.add(bt3open=new JButton("Open"));
		mentokT.add(bt3save=new JButton("Save"));
		Panel3.add(mentokT);
		JTextArea lbInfo3 = new JTextArea("Tegyuk fel, hogy adott egy n targybol allo halmaz, ahol az i-dik targy si meretere 0 <= si <=100 egysegnyi. Az osszes targyat szeretnenk bepakolni minimalis szamu 100 egysegnyi meretu ladakba. Minden ladaba a targyak barmely reszhalmaza belefer, amelynek osszmerete nem haladja meg a 100 egyseget. Ebben a panelben azt adom meg a targyakrol hogy, minimalisan hany ilyen ladaba fernenek bele. Ez a programmodul is hasznos lehet egy szallitmanyozo vallalat szamara. A LADAK EGYSEGEI MEGADASA UTAN RESULT!!",6,54);
		lbInfo3.setBackground(new Color(250,221,182));
		lbInfo3.setLineWrap(true);
		lbInfo3.setWrapStyleWord(true);
		lbInfo3.setEditable(false);
		Panel3.add(lbInfo3);
		Panel3.add(btTorol2 = new JButton("Torol"));
		Panel3.add(btEredmeny3=new JButton("Result"));
		Panel3.add(jlFigy3 = new JLabel(""));
		Panel3.add( jl100 = new JLabel("Csak 100 egys.-nyi ladak:"));
		Panel3.add(uzenet3=new JTextArea("",2,36));
		jlFigy3.setForeground(new Color(255,0,0));
		uzenet3.setForeground(new Color(0,0,255));
		uzenet3.setEditable(false);
		uzenet3.setLineWrap(true);
		uzenet3.setWrapStyleWord(true);
		btEredmeny3.addActionListener(this);
		btTorol2.addActionListener(this);
		bt3open.addActionListener(this);
		bt3save.addActionListener(this);
		fco3.setCurrentDirectory(new File("."));
		fco3.setFileFilter(new JavaFilter());
		fcs3.setCurrentDirectory(new File("."));
		fcs3.setFileFilter(new JavaFilter());
		mezok3();
		for(int i=1; i<=n20;i++)
		{
			tfAdatok3[i].addActionListener(this);
			tfAdatok3[i].addFocusListener(this);
		}
		//end Panel3 konstruktor DEKLARACIOI :
	}// end HaromPanel

	public void focusGained(FocusEvent e)
	{
		for(int i=1;i<=varossz;i++)
			for(int j=i+1;j<=varossz;j++)
			if(e.getComponent()==tfAdatok[i][j])
			{
				Point pont0=tfJLab[varossz-1][varossz].getLocation();
				tfAdatok[i][j].selectAll();
				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				Point pont=tfJLab[i][j].getLocation();
				if(pont0.getX()+tfAdatok[varossz-1][varossz].getWidth()+tfJLab[varossz-1][varossz].getWidth()-pont.getX()>screenSize.width)
					scrollbar1.getViewport().setViewPosition(new Point((int)pont.getX()-150,(int)pont.getY()-70));
				else
					scrollbar1.getViewport().setViewPosition(new Point((int)(pont0.getX()-screenSize.width+tfAdatok[varossz-1][varossz].getWidth()+tfJLab[varossz-1][varossz].getWidth()),(int)pont.getY()-70));
			}
		for(int i=1; i<=rnum;i++)
			if(e.getComponent()==tfAdatok2[i])
			{
				tfAdatok2[i].selectAll();
			}
		for(int i=1; i<=n20;i++)
			if(e.getComponent()==tfAdatok3[i])
			{
				tfAdatok3[i].selectAll();
			}
	}//end focusGained

	public void focusLost(FocusEvent e)
	{
	}

	public void actionPerformed(ActionEvent ev)
	{
		String str2;
		String bevitel2_2;
		for(int i=1;i<=varossz;i++)
			for(int j=i+1;j<=varossz;j++)
			if(ev.getSource()==tfAdatok[i][j])
			btEredmeny.doClick();
		for(int i=1; i<=rnum;i++)
			if(ev.getSource()==tfAdatok2[i])
					btEredmeny2.doClick();
		for(int i=1; i<=n20;i++)
				if(ev.getSource()==tfAdatok3[i])
					btEredmeny3.doClick();


		if(ev.getSource()==tfvn || ev.getSource()==btOk)
		{
			pontmemory=new Point(0,0);
			String str0=tfvn.getText();
	   		String bevitel1=str0.trim();
			if (!isNumber(bevitel1,4,100))
			{
	   			jlFigy.setText("Nem jo szam!!");
	   			tfvn.requestFocus();
	  			tfvn.selectAll();
			}
				else
			{
				varossz = Integer.parseInt(bevitel1);
		 		varossz2=varossz;
				uzenet.setText("Alap varosok szama:  "+varossz2);
				bt1save.setEnabled(true);
				btEredmeny.setEnabled(true);
				b1Open=true;
	    		jlFigy.setText("Jo szam   !!");
				for(int sor=1; sor<=vint;sor++)
					for(int oszlop=sor+1;oszlop<=vint;oszlop++)
					{
						Panel1.remove(tfJLab[sor][oszlop]);
						Panel1.remove(tfAdatok[sor][oszlop]);
					}
					mezok1(varossz);
					jlFigy.setText("");
						 		}
		}
		else if (ev.getSource()==btEredmeny)
		{
			String str0=tfvn.getText();
			String bevitel1=str0.trim();
			jlFigy.setText("");
			if (!isNumber(bevitel1,3,101))
			{
				jlFigy.setText("Nem jo szam!!");
				tfvn.requestFocus();
	  			tfvn.selectAll();
				return;
			}
			if(varossz2<Integer.valueOf(str0))
			{
				tfvn.setText(String.valueOf(varossz2));
				varossz=varossz2;
			}
			else
				varossz=Integer.valueOf(str0);
			MezokKezel1(false);
		}
		else if (ev.getSource()==tfRakasN || ev.getSource()==btEredmeny2)
		{
			MezokKezel2(false);
		}
		else if(ev.getSource()==btTorol)
			for(int i=1; i<=rnum;i++)
				tfAdatok2[i].setText("0");
		else if(ev.getSource()==btTorol2)
			for(int sor=1; sor<=n20;sor++)
				tfAdatok3[sor].setText("0");
		else if(ev.getSource()==btEredmeny3 )
		{
			uzenet3.setText("");
			jlFigy3.setText("");
			 MezokKezel3(false);
		}

		else if(ev.getSource()==bt1save)
		{
			tfvn.setText(String.valueOf(varossz));
			MezokKezel1(true);
			if(fcs1.showSaveDialog(this)==JFileChooser.APPROVE_OPTION)
			{
				update1save();
			}
		}
		else if(ev.getSource()==bt2save)
		{
			jlFigy2.setText("");
			MezokKezel2(true);
			if(!boolte && fcs2.showSaveDialog(this)==JFileChooser.APPROVE_OPTION )
			{
				update2save();
			}
		}
		else if(ev.getSource()==bt3save)
		{
			jlFigy3.setText("");
			MezokKezel3(true);
			if(!boolt && fcs3.showSaveDialog(this)==JFileChooser.APPROVE_OPTION )
			{
				update3save();
			}
		}
		else if (ev.getSource()==bt1open)
		{
			if(fco1.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
			{
				update1open();
			}
		}
		else if (ev.getSource()==bt2open)
		{
			if(fco2.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
			{
				update2open();
			}
		}
		else if (ev.getSource()==bt3open)
		{
			if(fco3.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
			{
				update3open();
			}
		}
	}//end actionPerformed

	boolean isNumber(String strcsucsok,int numVarMin,int numVarMax)
	{
		try
		{
			int tfvsza = Integer.parseInt(strcsucsok);
			if( tfvsza<numVarMin || tfvsza>numVarMax)
			throw new NumberFormatException();
			return true;
		}
			  catch( NumberFormatException ex)
	 	{
	   		  return false;
		}
	}//end isNumber

	void update1open()
	{
		File sfo1=fco1.getSelectedFile();
	  	if(sfo1!=null)
	  	{
	  		try
	  		{
				name1=sfo1.getName();
	  			BufferedReader in = new BufferedReader(new FileReader(sfo1));

	  			int n=Integer.parseInt(in.readLine());
	  			tfvn.setText(String.valueOf(n));

	  			btOk.doClick();

	  			for(int sor=1; sor<=n; sor++)
			       for(int oszlop=sor+1;oszlop<=n;oszlop++)
			        {
			  			tomBopen[sor][oszlop]=in.readLine();
			  			tfAdatok[sor][oszlop].setText(tomBopen[sor][oszlop]);
					}
 	 	  		boolean bool=false;
 	    		for(int sor=1; sor<=n;sor++)
 		     	  	for(int oszlop=sor+1;oszlop<=n;oszlop++)
	 	     	  	{
 		 	   			String str2 =tomBopen[sor][oszlop];
 			       		if (!isNumber(str2,0,2147483647) && bool==false)
 			       		{
 	        				JOptionPane.showMessageDialog(this,"Nincs, vagy nem jo adat!");
 	         				tfAdatok[sor][oszlop].requestFocus();
 	         				tfAdatok[sor][oszlop].selectAll();
 	         				bool=true;
 		 	  			}
 		         		 else
						utakTomb[sor-1][oszlop-1]=Integer.parseInt(str2);
						utakTomb[oszlop-1][sor-1]=Integer.parseInt(str2);
					}
 	     in.close();

 	 		    } catch (IOException ex) {
 	 		}
 	 	}
 	}//end update1open

	void  update2open()
	{
		File sfo2=fco2.getSelectedFile();
		  	if(sfo2!=null)
		  	{
		  		try
		  		{
		  			BufferedReader in = new BufferedReader(new FileReader(sfo2));
		  			String r=in.readLine();
		  			tfRakasN.setText(r);
		  			btTorol.doClick();
					for(int sor=1; sor<=rnum;sor++)
						tfAdatok2[sor].setText(in.readLine());
					boolean bool=false;
					for(int sor=1; sor<=rnum;sor++)
					{
				  	    String str2 =(tfAdatok2[sor].getText().trim());
				        if (!isNumber(str2,0,2147483647) && bool==false)
				        {
				            JOptionPane.showMessageDialog(this,"Nincs, vagy nem jo adat!");
				            tfAdatok2[sor].requestFocus();
					            tfAdatok2[sor].selectAll();
					            bool=true;
				  	  	}
					else
					fileTomb[sor]=Integer.parseInt(str2);
				  	}
				    if(!bool) ladaRakas(sfo2.getName());
					in.close();

		  		}catch (IOException ex) {
		  		}
		 }
	 }//end update2open

	void  update3open()
	{
		File sfo3=fco3.getSelectedFile();
		if(sfo3!=null)
		{
			try
			{
		  		BufferedReader in = new BufferedReader(new FileReader(sfo3));
				for(int sor=1; sor<=n20;sor++)
					tfAdatok3[sor].setText(in.readLine());
					boolean bool=false;
					for(int sor=1; sor<=n20;sor++)
					{
					    String str2 =(tfAdatok3[sor].getText());
				        if (!isNumber(str2,0,100) && bool==false)
				        {
				          	JOptionPane.showMessageDialog(this,"Nincs, vagy nem jo adat!");
				          	tfAdatok3[sor].requestFocus();
				          	tfAdatok3[sor].selectAll();
				          	bool=true;
					 	}
				          	else
							  tombA[sor-1]=Integer.parseInt(str2);
					}
				if(!bool) ladaPakolas(sfo3.getName());
		     	in.close();
		  	}catch (IOException ex) {
			}
		 }
	 }//end update3open

	 void update1save()
	 {
	 	int n=Integer.valueOf(varossz);
	 	File sfs1=fcs1.getSelectedFile();
	 	if(sfs1!=null)
	 	{
	   		try
	   		{
	 			String fname=sfs1.getAbsolutePath();
	 			if(!(sfs1.getName().toUpperCase().endsWith(".TXT")))
	 			{
	 				fname=fname+".txt";
	 			}
	 	  		BufferedWriter out1 = new BufferedWriter(new FileWriter(fname));
	 	    	out1.write(String.valueOf(n));
	 	    	out1.newLine();
	 	    	for(int sor=1; sor<=n; sor++)
	 	  			 for(int oszlop=sor+1;oszlop<=n;oszlop++)
	 	  			 {
	 	  				out1.write(tfAdatok[sor][oszlop].getText());
	 	  		 		out1.newLine();
	 				}
	 	 			out1.close();
	 	    }catch (IOException ex){
	   		}
	   	}
	}//end update1save

	void update2save()
	{
		int r=Integer.valueOf(tfRakasN.getText());
		File sfs2=fcs2.getSelectedFile();
		if(sfs2!=null)
	   	{
	  		try
	  		{
				String fname=sfs2.getAbsolutePath();
				if(!(sfs2.getName().toUpperCase().endsWith(".TXT")))
				{
					fname=fname+".txt";
				}
		  		BufferedWriter out2 = new BufferedWriter(new FileWriter(fname));
		    	out2.write(String.valueOf(r));
		    	out2.newLine();
		    	for(int sor=1; sor<=rnum; sor++)
				{
	  				out2.write(tfAdatok2[sor].getText());
	  		 		out2.newLine();
				}
				out2.close();

			}catch (IOException ex){
	  		}
	  	}
	 }//end update2save

	void update3save()
	{
		File sfs3=fcs3.getSelectedFile();
		if(sfs3!=null)
	   	{
	  		try
	  		{
				String fname=sfs3.getAbsolutePath();
				if(!(sfs3.getName().toUpperCase().endsWith(".TXT")))
				{
					fname=fname+".txt";
				}
		  		BufferedWriter out3 = new BufferedWriter(new FileWriter(fname));
		    	for(int sor=1; sor<=n20; sor++)
				{
	  				out3.write(tfAdatok3[sor].getText());
	  		 		out3.newLine();
				}
			out3.close();
		    }catch (IOException ex){
	  		}
	  	}
	}//end update3save

	void mezok1(int vnum)
	{
		for(int sor=1; sor<=varossz;sor++)
	      for(int oszlop=sor+1;oszlop<=varossz;oszlop++)
	       {
				Panel1.add(tfJLab[sor][oszlop]=new JLabel(sor+" -> "+oszlop+":"));
				Panel1.add(tfAdatok[sor][oszlop]=new JTextField(4));
				tfAdatok[sor][oszlop].setDocument(new JTextFieldLimit(6));
				tfAdatok[sor][oszlop].setText("1");
				if(sor%2==0)
					tfAdatok[sor][oszlop].setBackground(new Color(220,255,200));
				else
					tfAdatok[sor][oszlop].setBackground(new Color(200,255,50));
				tfAdatok[sor][oszlop].addFocusListener(this);
				tfAdatok[sor][oszlop].addActionListener(this);
			}
			Color szin;
		Panel1.setBackground(szin=new Color(230,generator.nextInt(253)+1,254));
		mentok1.setBackground(szin);
		kepecske1.setBackground(szin);
		tfAdatok[1][2].requestFocus();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point pont=tfJLab[varossz-1][varossz].getLocation();
		Point pont0=tfJLab[1][2].getLocation();
		if(pont.getX()-pont0.getX()<screenSize.width)

		scrollbar1.getViewport().setViewPosition(new Point((int)(screenSize.width-pont0.getX()-90),(int)pont.getY()-70));
		vint=vnum;
	}//end mezok1

	void mezok2(int vnum2)
	{
		for(int sor=1;sor<=vnum2; sor++)
		{
			Panel2.add(tfJLab2[sor]=new JLabel(sor+"."));
			Panel2.add(tfAdatok2[sor]=new JTextField(4));
			tfAdatok2[sor].setDocument(new JTextFieldLimit(6));
			tfAdatok2[sor].setText("0");
			if(sor%3==0)
			tfAdatok2[sor].setBackground(new Color(220,255,220));
			else if(sor%3==1)
				tfAdatok2[sor].setBackground(new Color(255,255,sor*10));
			else
				tfAdatok2[sor].setBackground(new Color(255,255,200));
		}
		Color szin;
		Panel2.setBackground(szin=new Color(255,generator.nextInt(254)+1,230));
		mentokL.setBackground(szin);
		kepecske2.setBackground(szin);
		tfAdatok2[1].requestFocus();
		//vint2=vnum2;
	}//end mezok2

	void mezok3()
	{
		for(int sor=1;sor<=n20; sor++)
		{
			Panel3.add(tfJLab3[sor]=new JLabel(sor+"."));
			Panel3.add(tfAdatok3[sor]=new JTextField(4));
			tfAdatok3[sor].setDocument(new JTextFieldLimit(3));
			if(sor%3==0)
				tfAdatok3[sor].setBackground(new Color(220,255,220));
			else if(sor%3==1)
				tfAdatok3[sor].setBackground(new Color(255,255,sor*10));
			else
	           tfAdatok3[sor].setBackground(new Color(255,255,200));
		 }
		 Color szin;
		 Panel3.setBackground(szin=new Color(255,generator.nextInt(254)+1,230));
		 mentokT.setBackground(szin);
		 kepecske3.setBackground(szin);
		 tfAdatok3[1].requestFocus();
		 btTorol2.doClick();
		//vint3=vnum3;
	}//end  mezok3


	void MezokKezel1(boolean isOpen)
	{
			Point pont;
		  boolean bool=false;
		  for(int sor=1; sor<=varossz;sor++)
	      for(int oszlop=sor+1;oszlop<=varossz;oszlop++)
	      {
				String str2 =(tfAdatok[sor][oszlop].getText().trim());
				if (!isNumber(str2,0,2147483647) && bool==false)
				{
					pont=tfJLab[sor][oszlop].getLocation();
					scrollbar1.getViewport().setViewPosition(new Point((int)pont.getX(),(int)pont.getY()-60));
					tfAdatok[sor][oszlop].selectAll();
	           		JOptionPane.showMessageDialog(this,"Nincs, vagy nem jo adat!");
					tfAdatok[sor][oszlop].requestFocus();
	          		bool=true;
				}
				else
					utakTomb[sor-1][oszlop-1]=Integer.parseInt(str2);
					utakTomb[oszlop-1][sor-1]=Integer.parseInt(str2);
			}
			if(!bool && !isOpen)
			{
				TSPopti=new Thread(new TSPopt(utakTomb,varossz,""));
				TSPopti.setPriority(1);
				TSPopti.start();
			}
	}//end MezokKezel1

	 void MezokKezel2(boolean isOpen)
	 {
		 String str2;
		 String bevitel2_2;
		 uzenet2.setText("");
		 str2=tfRakasN.getText();
		 bevitel2_2=str2.trim();
		 if (!isNumber(bevitel2_2,1,15))
		 {
		 	boolte=true;
		 	tfRakasN.requestFocus();
		 	tfRakasN.selectAll();
		 	jlFigy2.setText("Nem jo szam!!");
		 	return;
		}
		else rakasNum = Integer.parseInt(bevitel2_2);
		jlFigy2.setText("");
		boolte=false;
		for(int sor=1; sor<=rnum;sor++)
		{
		    str2 =(tfAdatok2[sor].getText().trim());
	        if (!isNumber(str2,0,2147483647) && boolte==false)
	        {
				JOptionPane.showMessageDialog(this,"Nincs, vagy nem jo adat!");
				tfAdatok2[sor].requestFocus();
				tfAdatok2[sor].selectAll();
				boolte=true;
			}
	 			else
			fileTomb[sor]=Integer.parseInt(str2);
		}
		if(!boolte && !isOpen) ladaRakas("");
	}//end MezokKezel2

	void MezokKezel3(boolean isOpen)
	{
		boolt=false;
		for(int sor=1; sor<=n20;sor++)
		{
			String str2 =(tfAdatok3[sor].getText().trim());
			if (!isNumber(str2,0,100) && boolt==false)
			{
				JOptionPane.showMessageDialog(this,"Nincs, vagy nem jo adat!");
				tfAdatok3[sor].requestFocus();
				tfAdatok3[sor].selectAll();
				boolt=true;
			}
			else
			tombA[sor-1]=Integer.parseInt(str2);
			}
	     if(!boolt && !isOpen) ladaPakolas("");
	}//end MezokKezel3

	void ladaRakas(String name2)
	//name2: ha megnyitott file-bol toltott adatokat, a file neve, amit tovabbad a grafikus megjelenitesnek
	{

		final int db = 200;
		long[] memk,mem1,mem2,meml;
	    memk= new long[db];
	    mem1= new long[db];
		mem2= new long[db];
	    meml= new long[db];
		long[][]t   = new long[db][db];
		int[][][]rak  = new int[db][db][db];
		int k=0,n=0,r;
		long memo,minim,eredmeny,mag;
		String st="0";
		int []tombom=new int [db];
		int i=1;
		n = rnum;
		k = Integer.parseInt(tfRakasN.getText());
	// * seged tombokvektorok elkeszitese  * //

		memk[1]=fileTomb[1];
		memo=fileTomb[1]; mem1[1]=0; mem1[2]=fileTomb[1];
		for(i = 2; i<=n; i++)
		{
			memo=fileTomb[i]+memo;
			memk[i]=memo;
			mem1[i+1]=memo+mem1[i];
		}
		memo=fileTomb[n]; mem2[n] =0; mem2[n-1]=fileTomb[n];
		meml[1]=fileTomb[n];
		for(i=n-1; i>=2; i--)
		{
			memo=fileTomb[i]+memo;
			meml[i]=memo;
			mem2[i-1]=memo + mem2[i];
		}

		 // a t[] [] tomb feltoltese dinamikusan

		// Minden i,j-re  ki kellene szamolni a t(i,j) erteket,
		// ami a minimalis rakodasi koltseg azon feltetelek
		// mellett, hogy legfeljebb i kulonbozo helyre rakodunk, es az
		// utolso rakodasi hely a j-edik  kontenerhely.
		// A vegso valasz, akkor  a min{T(k,i) :i=1,..,n}), ahol
		// n a kontenerek szama, k pedig a rakodasra
		// megengedett helyek szama..
		// a megfeleo j - edik hely erteket az elozo sor felhasznalasaval halytom vegre
		// dinamukus modon tehat.

		long valtozo=0;
		for ( i= 1; i<=n; i++)
		{
			t[1][i]=mem1[i]+mem2[i];
			rak[1][i][1]=i;
		}
		int e=0;

		for( i=2; i<=k; i++)
		{
			for ( int j=i; j<=n; j++)
			{
				minim=2001*200*200;
				mag=0;
				for ( r=i-1; r<=j-1; r++)
				{
					valtozo=t[i-1][r];
					for(int z=((j+r) / 2)+1;z<=n;z++)
					{
						valtozo=valtozo-fileTomb[z]*(z-r)+fileTomb[z]*Math.abs(j-z);
					}
					if (minim>valtozo)
					{
						minim = valtozo;
						e=r;
					}
				}
				t[i][j]=minim;
				for(int o=1;o<=i;o++)
				{
					if(o==i)rak[i][j][o]=j;
						else
					rak[i][j][o]=rak[i-1][e][o];
				}
			}
		}
		// * a minimalis eredmeny megkeresese a t tombbol

		int g=0;
		eredmeny=201*201*2001;
		for (i=k; i<=n; i++)
		{
			if(eredmeny > t[k][i])
			{
				eredmeny=t[k][i];
				g=i;
			}
		}
		for(i=1;i<=k;i++)
			tombom[i]=rak[k][g][i];

		uzenet2.setText("A "+(n)+" db ladat "+eredmeny+" egysegnyi munkaval lehet legkonyebben "+(k)+" db helyre egymasba rakni.");
	    new LadaGraphic(n,eredmeny,tombom,k,fileTomb,name2);
	    //n: a kontinerek szama, eredmeny: a opt. munka koltsege, tombom: a rakasok helyei, k: a rakasok szama, fileTomb: a kontenerek sulyai, name2: ha megnyitott file-bol toltott adatokat, a file neve, amit tovabbad a grafikus megjelenitesnek
	}//end ladaRakas

	void ladaPakolas(String name3)
	//name3: Ha megyitott file-bol dolgozik a program, a file neve, amit tovabbad a grafikus megjelenitesnek
	{
		int [] tombB = new int [n20];
		int [][] tombC = new int [n20][n20];

	// a program lelke : a tombA[j]-ben taroltam a beolvasott adatokat
	// es hasonlitom mindig ossze a forciklusok segitsegevel a 100-as
	// szamokat tartalmazo tombB[i]-vel. Tehat mindig az elejerol
	// vizsgalom az elferhetoseget.
		for(int j=0;j<n20;j++)
		{
			tombB[j]=100;
			for(int i=0; i<n20; i++)
			tombC[j][i]=0;
		}
		for(int i=0; i<n20; ++i)
			for(int j=0; j<n20; j++)
				if ((tombB[j]-tombA[i])>=0)
				{
					tombB[j]-=tombA[i];
					tombC[j][i]=i;
					break;
				}
		int n=0;
	 	for(int i=0; i<n20; ++i)
		if(tombB[i]!=100)           // amelyik erteke a tombnek nem 100, akkor az a lada hasznalat alatt van
		n++;
		uzenet3.setText("A minimalisan felhasznalt, 100 egysegnyi ladak szama "+(n)+".");
		new TargyGraphic(tombA,tombC,n20,n,name3);
		//tombA: a targyak //tombCC : a megtelt zsakok// n: a zsakok szama//name3: Ha megyitott file-bol dolgozik a program, a file neve, amit tovabbad a grafikus megjelenitesnek
	}//end ladaPakolas
}//class HaromPanel

//*****************

class Targyak extends JPanel
{
	private Color color;
	private int nagysag;
	private int nta;
  	public Targyak(Color color,int nagysag,int nta)
	{
	 	this.color=color;
	 	this.nagysag=nagysag;
	 	this.nta=nta;
	 }
	 protected void paintComponent(Graphics gr)
	 {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		super.paintComponent(gr);
		gr.setColor(color);
		int szeleseg=0;
		if(nta>10)
		szeleseg=screenSize.width/nta-12;
		else
		szeleseg=80;
		gr.fillRect(5,5,szeleseg,(int)(nagysag*3/5));
		gr.setColor(Color.BLACK);
		gr.drawRect(5,5,szeleseg,(int)(nagysag*3/5));
	}
}//class Targyak

//***********************


class TargyGraphic extends JFrame
{
  	private Container cp= getContentPane();
	private int[][]tombGrC;
	private int nt;
	private int nl;
	private int[]tombGrA;//tombA: a targyak //tombCC : a megtelt zsakok// n: a zsakok szama
	private String nev3;
	static int szamlalo=1;

	public TargyGraphic(int []tombGrA,int[][]tombGrC,int nt,int nl,String nev3)
	{
  		this.tombGrC=tombGrC;
  		this.nl=nl;
  		this.tombGrA=tombGrA;
  		this.nt=nt;
  		this.nev3=nev3;
  		setDefaultCloseOperation(HIDE_ON_CLOSE);
  		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  		int magasag=(int)(screenSize.height/3*1.8);
  		int magasag2=(int)(screenSize.height-magasag);
  		setBounds(0,magasag,screenSize.width,magasag2);
  		setTitle((szamlalo++)+". Targyak Graphic "+nev3);
  		JPanel targyak=new JPanel();
  		JPanel targyakAdat=new JPanel();
  		JLabel JLadat=new JLabel();
  		cp.setLayout(new GridLayout(3,0));
  		targyak.setLayout(new GridLayout(1,nt,3,10));
  		targyakAdat.setLayout(new GridLayout(1,nt,3,10));
  		for(int i=0;i<nt;i++)
  		{
  			if(i%3==0)
  				targyak.add(new Targyak(Color.RED,tombGrA[i],nt));
  			else if(i%3==1)
  				targyak.add(new Targyak(Color.GREEN,tombGrA[i],nt));
  			else
  				targyak.add(new Targyak(Color.BLUE,tombGrA[i],nt));
  			targyakAdat.add(new JLabel((i+1)+": "+tombGrA[i]));
		}
		cp.add(targyak);
		cp.add(targyakAdat);
		cp.add(new JLabel("A megoldas egyszeru: vesszuk a targyakat es beleteszuk az elso olyan ladaba amibe meg belefer. A fenti targyak "+nl+" ilyen ladaba fernenek el."));
		show();
	}
}//class TargyGraphic

//********************

class Kep extends JPanel
{
	private Image img;
	private String fileName;
	private MediaTracker tr;
	private int ladnum;
	public Kep(String fileName,int ladnum)
	{
		this.fileName=fileName;
		this.ladnum=ladnum;
		img= Toolkit.getDefaultToolkit().createImage(fileName);
		tr=new MediaTracker(this);
		tr.addImage(img,0);
		try
		{
			tr.waitForID(0);
		}
		catch (InterruptedException ex){
		}
		finally
		{
			tr.removeImage(img,0);
		}
	}
	protected void paintComponent(Graphics gr)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		super.paintComponent(gr);
		if(ladnum<9)
		gr.drawImage(img,0,0,screenSize.width/3*2/7-25,screenSize.width/3*2/7-25,this);
		else
		gr.drawImage(img,0,0,screenSize.width/3*2/ladnum-3,screenSize.width/3*2/ladnum-3,this);
	}
}// class Kep

//***************

class StrSzamokLada extends JPanel
{
	private long Strint;
	private Color color;
	private int ladan;
	public StrSzamokLada(Color color,long Strint,int ladan)
	{
		this.color=color;
		this.Strint=Strint;
		this.ladan=ladan;
	}
	protected void paintComponent(Graphics gr)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		super.paintComponent(gr);
		String STRstrint=String.valueOf(Strint);
		gr.setColor(color);
		if (ladan<7)
			gr.setFont(new Font("TimesRoman",Font.PLAIN,20));
		gr.drawString(STRstrint,0,25);
	}
}//end class StrSzamokLada

//*****************

class LadaGraphic extends JFrame
{
	private int ladnum;
	private long eredmenye;
	private int [] dbbox;
	private Container cp= getContentPane();
    private int k;
    private JLabel jLeredm;
    private String nev2;
    static int szamlal=1;
    public LadaGraphic(int ladnum,long eredmenye,int [] dbbox,int k,long[] fileTombG,String nev2)
	{
		this.ladnum=ladnum;
		this.eredmenye=eredmenye;
		this.dbbox=dbbox;
		this.k=k;
		this.nev2=nev2;
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,screenSize.height/3*2,screenSize.width/3*2+120,screenSize.height/3);
		setTitle((szamlal++)+". LadaGraphic Graphic "+nev2);
		JPanel ladjpan=new JPanel();
		JPanel ladjpszamok=new JPanel();
		cp.setLayout(new GridLayout(3,0));
		ladjpan.setLayout(new GridLayout(1,ladnum,10,10));
		ladjpszamok.setLayout(new GridLayout(1,ladnum,10,10));
		String fileNameAkt="";
		boolean bvan=false;
		for(int i=1;i<=ladnum;i++)
		{
			bvan=false;
			for(int j=1;j<=k;j++)
			{
				if(dbbox[j]==i)
				{
					fileNameAkt="lada_"+i+".jpg";
					ladjpszamok.add(new StrSzamokLada(Color.RED,fileTombG[i],ladnum));
					bvan=true;
					break;
				}
				else
					fileNameAkt="ladablack_"+i+".jpg";
			}
			if(!bvan)
				ladjpszamok.add(new StrSzamokLada(Color.BLACK,fileTombG[i],ladnum));
			ladjpan.add(new Kep(fileNameAkt,ladnum));
		}
		cp.add(ladjpan);
		cp.add(ladjpszamok);
		cp.add(jLeredm=new JLabel("  "+k+"  rakas.	   AZ igy kialakult minimalis munka koltsege: "+eredmenye+".  A rakasok a piros ladak helyei."));
		show();
	}
}//end class LadaGraphic

//*****************************

class KepKamion extends JPanel
{
	private Image img;
	private String fileName;
	private MediaTracker tr;
	public KepKamion(String fileName)
	{
		img= Toolkit.getDefaultToolkit().createImage(fileName);
		tr=new MediaTracker(this);
		tr.addImage(img,0);
		try
		{
			tr.waitForID(0);
		}
		catch (InterruptedException ex)
		{
		}
		finally
		{
			tr.removeImage(img,0);
		}
	}
	protected void paintComponent(Graphics gr)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		super.paintComponent(gr);
		gr.drawImage(img,0,0,screenSize.width/3,screenSize.height/3,this);
	}
}//end class KepKamion

//******************

class StrCim extends JPanel
{
	private String Strint;
	private Color color;
	public StrCim(Color color,String Strint)
	{
		this.color=color;
		this.Strint=Strint;
	}
	protected void paintComponent(Graphics gr)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		super.paintComponent(gr);
		gr.setColor(color);
		gr.setFont(new Font("TimesRoman",Font.ITALIC,50));
		gr.drawString(Strint,0,100);
	}
}//end class StrCim

//*******************

class KepGif2 extends JPanel
{
	private Image img;
	private String fileName;
	private MediaTracker tr;
	public KepGif2(String fileName)
	{
		img= Toolkit.getDefaultToolkit().createImage(fileName);
		tr=new MediaTracker(this);
		tr.addImage(img,0);
		try
		{
			tr.waitForID(0);
		}
		catch (InterruptedException ex)
		{
		}
		finally
		{
			tr.removeImage(img,0);
		}
	}
	protected void paintComponent(Graphics gr)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		super.paintComponent(gr);
		gr.drawImage(img,screenSize.width/6-35, screenSize.height/6-25,70,50,this);
	}
}//end class KepGif2

public class Szallitmanyozo_Ceg_21 extends JFrame implements MouseListener
{
	JWindow win;
	public Szallitmanyozo_Ceg_21()
	{
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0,0,screenSize.width, screenSize.height);
		setSize(40000,40000);
		setTitle("Szallitmanyozo ceg 2.1");
		HaromPanel hpan= new HaromPanel();
		getContentPane().add(hpan);
		win = new JWindow(this);
		win.addMouseListener(this);
		win.setLayout(new GridLayout(3,1));
		win.getContentPane().add(new StrCim(new Color(0,0,255),"SZALLITMANYOZO CEG 2.1"));
		JPanel JPkoz=new JPanel();
		JPkoz.add(new KepGif2("fold2.gif"));
		JPkoz.add(new KepKamion("kamion.jpg"));
		JPkoz.add(new KepGif2("fold2.gif"));
		JPkoz.setLayout(new GridLayout(1,3));
		win.getContentPane().add(JPkoz);
		win.getContentPane().add(new StrCim(new Color(255,0,0),"Szakdolgozat: Dinnyes Balazs; dibmaat.sze"));
		win.setBounds(0,0,screenSize.width, screenSize.height);
		win.show();
		setEnabled(false);
		show();
		}


	public void mouseClicked(MouseEvent ev)
	{
		if(ev.getSource()==win)
		{
		win.hide();
		setEnabled(true);
		}
	}
	public void mousePressed(MouseEvent ev)
	{
	}
	public void mouseReleased(MouseEvent ev)
	{
	}
	public void mouseEntered(MouseEvent ev)
	{
	}
	public void mouseExited(MouseEvent ev)
	{
	}

 	public static void main(String args[])
 	{
		new Szallitmanyozo_Ceg_21();
	}
}// class Szallitmanyozo_Ceg_20

//*****************************************************************************************************************//

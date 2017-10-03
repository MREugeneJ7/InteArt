import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class VentanaEntorno extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entorno backEnd;
	private JTable matriz;
	private JTextField N,M;
	private JPanel panelContenido;
	public void actionPerformed(ActionEvent e) {
		Object[]dummy;
		backEnd= new Entorno(Integer.parseInt(N.getText()),Integer.parseInt(M.getText()));
		dummy = new String[backEnd.getMatriz().length];
		dummy = backEnd.getMatriz()[0];
		matriz.setModel(new DefaultTableModel(backEnd.getMatriz(),dummy)) ;
		matriz.setTableHeader(null);
		panelContenido.revalidate();
	}
	
	public VentanaEntorno(Entorno x)
	{
		this.backEnd=x;
		N = new JTextField(4);
		M = new JTextField(4);
		JButton traducir = new JButton("Crear Matriz");
		backEnd = new Entorno(10,10);
		matriz = new JTable();
		traducir.addActionListener(this);
		panelContenido = new JPanel();
		panelContenido.add(N);
		panelContenido.add(M);
		panelContenido.add(traducir);
		panelContenido.add(matriz);
		this.setContentPane(panelContenido);
		this.setTitle("Coche Inteligente");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(150, 150);
		this.setSize(250, 125);
		this.setResizable(true);
		this.setVisible(true);
	}
	
}

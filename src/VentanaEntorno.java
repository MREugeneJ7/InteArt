import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.*;

public class VentanaEntorno extends JFrame implements ActionListener, TableModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Entorno backEnd;
	private JTable matriz;
	private JTextField N,M,percent;
	private JPanel panelContenido;
	private JButton changePercent;
	private JLabel aviso,info;
	public void actionPerformed(ActionEvent e) {
		Object[]dummy;
		if(e.getSource()==changePercent)
		{
			backEnd.setPorcentaje(Integer.parseInt(percent.getText()));
		}
		else
		{
			try {
				backEnd= new Entorno(Integer.parseInt(N.getText()),Integer.parseInt(M.getText()));
				aviso.setVisible(false);
			} catch (NumberFormatException e1) {
				aviso.setVisible(true);
			} catch (ConstructorException e1) {
				aviso.setVisible(true);
			}
			dummy = new String[backEnd.getMatriz().length];
			dummy = backEnd.getMatriz()[0];
			matriz.setModel(new DefaultTableModel(backEnd.getMatriz(),dummy)) ;
			matriz.setTableHeader(null);
			matriz.getModel().addTableModelListener(this);
		}
		info.setText("DIM:"+backEnd.getMatriz().length+"x"+backEnd.getMatriz()[0].length+"|Porecntaje:"+backEnd.getPorcentaje());
		panelContenido.revalidate();
		pack();
	}

	public VentanaEntorno(Entorno x)
	{
		panelContenido = new JPanel();
		GroupLayout layout = new GroupLayout(panelContenido);
		panelContenido.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.backEnd=x;
		aviso = new JLabel("Matriz incorrecta");
		info = new JLabel("DIM:"+backEnd.getMatriz().length+"x"+backEnd.getMatriz()[0].length+"|Porecntaje:"+backEnd.getPorcentaje());
		aviso.setVisible(false);
		aviso.setForeground(Color.red);
		N = new JTextField(4);
		M = new JTextField(4);
		percent = new JTextField(Integer.toString(backEnd.getPorcentaje()));
		JButton dibujar = new JButton("Crear Matriz");
		changePercent = new JButton("%");
		matriz = new JTable();
		dibujar.addActionListener(this);
		changePercent.addActionListener(this);
		matriz.getModel().addTableModelListener(this);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(N)
						.addComponent(M)
						.addComponent(dibujar)
						.addComponent(percent)
						.addComponent(changePercent))
				.addComponent(matriz)
				.addGroup(layout.createSequentialGroup()
						.addComponent(aviso)
						.addComponent(info))
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(N)
						.addComponent(M)
						.addComponent(dibujar)
						.addComponent(percent)
						.addComponent(changePercent))
				.addComponent(matriz)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(aviso)
						.addComponent(info))
				);
		this.setContentPane(panelContenido);
		this.setTitle("Coche Inteligente");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(0, 0);
		pack();
		setMaximumSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setResizable(true);
		this.setVisible(true);
	}

	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel)e.getSource();
		Object data = model.getValueAt(row, column);
		if("c".equals(data))
		{
			backEnd.setMatriz(row,column,new Coche());
		}
		else if("M".equals(data))
		{
			backEnd.setMatriz(row,column,new Meta());
		}
		else if("o".equals(data))
		{
			backEnd.setMatriz(row,column,new Obstaculo());
		}
		else
		{
			backEnd.setMatriz(row,column,new Miembros());
		}
		aviso.setVisible(!backEnd.test());
		info.setText("Manual Override");
		panelContenido.revalidate();
		pack();
	}

}

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * IA Pract1
 * VentanaEntorno.java
 * Purpose: Clase que define la GUI.
 *
 * @author G.P.A (GUI Preciosa y Asombrosa)
 * @version 0.10 8/10/2017
 */

public class VentanaEntorno extends JFrame implements ActionListener, TableModelListener {

	private static final long serialVersionUID = 1L;
	private Entorno backEnd;
	private JTable matriz;
	private JTextField n,m,percent;
	private JPanel panelContenido;
	private JButton changePercent, createFromFile;
	private JLabel aviso,info;
	private final JFileChooser fc = new JFileChooser();
	/**
	 * Metodo que observa las acciones realizadas en la interfaz grafica
	 * 
	 * @param e Evento que lanzo este metodo
	 */
	public void actionPerformed(ActionEvent e) {
		Object[]dummy;
		if(e.getSource() == changePercent) backEnd.setPorcentaje(Integer.parseInt(percent.getText()));
		else if(e.getSource() == createFromFile) {
			int returnVal = fc.showOpenDialog(panelContenido);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            try {
					backEnd = new Entorno(file.getAbsolutePath());
					aviso.setVisible(false);
				} catch (IOException | ConstructorException | NumberFormatException e1) {
					aviso.setVisible(true);
				}
	        } else {
	            System.out.println("Open command cancelled by user.");
	        }
	        dummy = new String[backEnd.getMatriz().length];
			dummy = backEnd.getMatriz()[0];
			matriz.setModel(new DefaultTableModel(backEnd.getMatriz(),dummy)) ;
			matriz.setTableHeader(null);
			matriz.getModel().addTableModelListener(this);
		} else{
			try {
				backEnd= new Entorno(Integer.parseInt(n.getText()),Integer.parseInt(m.getText()));
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
		info.setText("DIM:" + backEnd.getMatriz().length + "x" + backEnd.getMatriz()[0].length + "|Porecntaje:" + backEnd.getPorcentaje());
		panelContenido.revalidate();
		pack();
	}
	/**
	 * Constructor de la ventana
	 * @param x Entorno que se muestra en la JTable matriz
	 */
	public VentanaEntorno(Entorno x) {
		panelContenido = new JPanel();
		GroupLayout layout = new GroupLayout(panelContenido);
		panelContenido.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		this.backEnd = x;
		aviso = new JLabel("Matriz incorrecta");
		info = new JLabel("DIM:" + backEnd.getMatriz().length + "x" + backEnd.getMatriz()[0].length + "|Porecntaje:" + backEnd.getPorcentaje());
		aviso.setVisible(false);
		aviso.setForeground(Color.red);
		n = new JTextField(4);
		m = new JTextField(4);
		percent = new JTextField(Integer.toString(backEnd.getPorcentaje()));
		JButton dibujar = new JButton("Crear Matriz");
		changePercent = new JButton("%");
		createFromFile = new JButton("Elegir Fichero");
		matriz = new JTable();
		dibujar.addActionListener(this);
		changePercent.addActionListener(this);
		matriz.getModel().addTableModelListener(this);
		createFromFile.addActionListener(this);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(n)
						.addComponent(m)
						.addComponent(dibujar)
						.addComponent(percent)
						.addComponent(changePercent))
				.addComponent(matriz)
				.addGroup(layout.createSequentialGroup()
						.addComponent(aviso)
						.addComponent(info)
						.addComponent(createFromFile))
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(n)
						.addComponent(m)
						.addComponent(dibujar)
						.addComponent(percent)
						.addComponent(changePercent))
				.addComponent(matriz)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(aviso)
						.addComponent(info)
						.addComponent(createFromFile))
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
	/**
	 * Metodo que observa cambios realizados en la JTable matriz
	 * @param e Evento que lanzo este metodo
	 */
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
		int column = e.getColumn();
		TableModel model = (TableModel)e.getSource();
		Object data = model.getValueAt(row, column);
		if("c".equals(data)) backEnd.setMatrizCell(row,column,new Coche());
		else if("M".equals(data)) backEnd.setMatrizCell(row,column,new Meta());
		else if("o".equals(data)) backEnd.setMatrizCell(row,column,new Obstaculo());
		else backEnd.setMatrizCell(row,column,new Miembros());
		aviso.setVisible(!backEnd.test());
		info.setText("Manual Override");
		panelContenido.revalidate();
		pack();
	}

}

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
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
 * @version 1.1.c 10/10/2017
 */

public class VentanaEntorno extends JFrame implements ActionListener, TableModelListener, ChangeListener {

	private static final long serialVersionUID = 1L;
	private Entorno backEnd;
	private JTable matriz;
	private JTextField n,m,percent;
	private JPanel panelContenido;
	private JButton changePercent, createFromFile, solve, dibujar;
	private JLabel aviso,info;
	private JScrollPane panelMatriz;
	private final JFileChooser fc = new JFileChooser();
	private boolean timerStopper = false;
	private int j = 0;
	private javax.swing.Timer timer = new javax.swing.Timer(100, this);
	protected AudioFormat audioFormat;
	protected AudioInputStream audioInputStream;
	protected SourceDataLine sourceDataLine;
	protected boolean stopPlayback = false;
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
			matriz.setModel(new DefaultTableModel(backEnd.getMatriz(),dummy));
			matriz.setTableHeader(null);
			matriz.getModel().addTableModelListener(this);
		} else if(e.getSource() == solve) {
			if(backEnd.test()) {
				solve();
				j = 0;
			} else aviso.setVisible(true);
		} else if(e.getSource() == timer){
			if(timerStopper) return;
			backEnd.showOptimalStep(j);
			j++;
			if(!backEnd.test() && j != 0)  { 
				createFromFile.setEnabled(true);
				dibujar.setEnabled(true);
				stopPlayback = true;
				timerStopper = true;
				}
			dummy = new String[backEnd.getMatriz().length];
			dummy = backEnd.getMatriz()[0];
			matriz.setModel(new DefaultTableModel(backEnd.getMatriz(),dummy)) ;
			matriz.setTableHeader(null);
			matriz.getModel().addTableModelListener(this);
			for(int i = 0; i < matriz.getColumnCount();i++) {
				matriz.getColumnModel().getColumn(i).setPreferredWidth(20);
				matriz.getColumnModel().getColumn(i).setWidth(20);
			}
			panelMatriz.setPreferredSize(new Dimension ((int)matriz.getRowHeight()*backEnd.getMatriz().length, (int)matriz.getRowHeight()*backEnd.getMatriz()[0].length+3));
			panelMatriz.setColumnHeader(null);
			scrollToCenter(matriz,backEnd.getCoche().getX(), backEnd.getCoche().getY());
			panelMatriz.revalidate();
			panelContenido.revalidate();
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
		info.setText("DIM:" + backEnd.getMatriz().length + "x" + backEnd.getMatriz()[0].length + "|Porecntaje:" + backEnd.getPorcentaje() + "|TTS:" + backEnd.getTTS() + "|Num.Pasos:" + backEnd.getVisitados().size());
		for(int i = 0; i < matriz.getColumnCount();i++) {
			matriz.getColumnModel().getColumn(i).setPreferredWidth(20);
			matriz.getColumnModel().getColumn(i).setWidth(20);
		}
		panelMatriz.setPreferredSize(new Dimension ((int)matriz.getRowHeight()*backEnd.getMatriz().length, (int)matriz.getRowHeight()*backEnd.getMatriz()[0].length+3));
		panelMatriz.setColumnHeader(null);
		if(timerStopper) timer.stop();
		panelMatriz.revalidate();
		panelContenido.revalidate();
		if (e.getSource() != timer && e.getSource() != solve) pack();
	}
	private void scrollToCenter(JTable matriz2, int x, int y) {
			    if (!(matriz2.getParent() instanceof JViewport)) {
			      return;
			    }
			    JViewport viewport = (JViewport) matriz2.getParent();
			    Rectangle rect = matriz2.getCellRect(x, y, true);
			    Rectangle viewRect = viewport.getViewRect();
			    rect.setLocation(rect.x - viewRect.x, rect.y - viewRect.y);

			    int centerX = (viewRect.width - rect.width) / 2;
			    int centerY = (viewRect.height - rect.height) / 2;
			    if (rect.x < centerX) {
			      centerX = -centerX;
			    }
			    if (rect.y < centerY) {
			      centerY = -centerY;
			    }
			    rect.translate(centerX, centerY);
			    viewport.scrollRectToVisible(rect);
			}
		
	/**
	 * Resuelve el problema
	 */
	private void solve() {
		timerStopper = false;
		dibujar.setEnabled(false);
		createFromFile.setEnabled(false);
		backEnd.restartLists();
		try {
			backEnd.solve();
		} catch (BadMatrixException e) {
			aviso.setVisible(true);
			dibujar.setEnabled(true);
			createFromFile.setEnabled(true);
			stopPlayback = true;
			timerStopper = true;
		} finally {
			playAudio();
			timer.start();
		}	
	}
	private void playAudio() {
		    try{
		      File soundFile =
		                   new File("brumbrum.wav");
		      audioInputStream = AudioSystem.
		                  getAudioInputStream(soundFile);
		      audioFormat = audioInputStream.getFormat();
		      System.out.println(audioFormat);

		      DataLine.Info dataLineInfo =
		                          new DataLine.Info(
		                            SourceDataLine.class,
		                                    audioFormat);

		      sourceDataLine =
		             (SourceDataLine)AudioSystem.getLine(
		                                   dataLineInfo);

		      //Create a thread to play back the data and
		      // start it running.  It will run until the
		      // end of file, or the Stop button is
		      // clicked, whichever occurs first.
		      // Because of the data buffers involved,
		      // there will normally be a delay between
		      // the click on the Stop button and the
		      // actual termination of playback.
		      new PlayThread().start();
		    }catch (Exception e) {
		      e.printStackTrace();
		      System.exit(0);
		    }//end catch
		  }//end playAudio
		
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
		dibujar = new JButton("Crear Matriz");
		changePercent = new JButton("%");
		createFromFile = new JButton("Elegir Fichero");
		solve = new JButton("Solve");
		matriz = new JTable(){
	        /**
			 * 
			 */
			private static final long serialVersionUID = 3297034059953056855L;

			@Override
	        public Component prepareRenderer(TableCellRenderer renderer, int rowIndex,
	                int columnIndex) {
	            JComponent component = (JComponent) super.prepareRenderer(renderer, rowIndex, columnIndex);  

	            if(getValueAt(rowIndex, columnIndex).toString().equalsIgnoreCase("c")) {
	                component.setBackground(Color.RED);
	            } else if(getValueAt(rowIndex, columnIndex).toString().equalsIgnoreCase("o")){
	                component.setBackground(Color.BLACK);
	            } else if(getValueAt(rowIndex, columnIndex).toString().equalsIgnoreCase("M")){
	                component.setBackground(Color.GREEN);
	            } else if(getValueAt(rowIndex, columnIndex).toString().equalsIgnoreCase("F")){
	                component.setBackground(Color.YELLOW);
	            } else if(getValueAt(rowIndex, columnIndex).toString().equalsIgnoreCase(".")){
	                component.setBackground(Color.WHITE);
	            }

	            return component;
	        }
	    };
		dibujar.addActionListener(this);
		changePercent.addActionListener(this);
		matriz.getModel().addTableModelListener(this);
		panelMatriz = new JScrollPane(matriz);
		panelMatriz.setColumnHeader(null);
		panelMatriz.setPreferredSize(new Dimension(0,0));
		panelMatriz.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		matriz.setRowHeight(20);
		matriz.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		createFromFile.addActionListener(this);
		solve.addActionListener(this);
		JSlider timerSpeed = new JSlider(JSlider.VERTICAL,
                1, 200, 100);
		timerSpeed.addChangeListener(this);
		timerSpeed.setInverted(true);
		layout.setHorizontalGroup(
				layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(n)
						.addComponent(m)
						.addComponent(dibujar)
						.addComponent(percent)
						.addComponent(changePercent))
				.addGroup(layout.createSequentialGroup()
						.addComponent(panelMatriz)
						.addComponent(timerSpeed))
				.addGroup(layout.createSequentialGroup()
						.addComponent(aviso)
						.addComponent(info)
						.addComponent(createFromFile)
						.addComponent(solve))
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(n)
						.addComponent(m)
						.addComponent(dibujar)
						.addComponent(percent)
						.addComponent(changePercent))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(panelMatriz)
						.addComponent(timerSpeed))
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(aviso)
						.addComponent(info)
						.addComponent(createFromFile)
						.addComponent(solve))
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
		else if("M".equals(data)) backEnd.setMatrizCell(row,column,new Meta(new Coordenada (row,column)));
		else if("o".equals(data)) backEnd.setMatrizCell(row,column,new Obstaculo());
		else backEnd.setMatrizCell(row,column,new Miembros());
		aviso.setVisible(!backEnd.test());
		info.setText("Manual Override");
		panelMatriz.revalidate();
		panelContenido.revalidate();
		pack();
	}
	//=============================================//
	//Inner class to play back the data from the
	// audio file.
	class PlayThread extends Thread{
	  byte tempBuffer[] = new byte[10000];

	  public void run(){
	    try{
	      sourceDataLine.open(audioFormat);
	      sourceDataLine.start();

	      int cnt;
	      //Keep looping until the input read method
	      // returns -1 for empty stream or the
	      // user clicks the Stop button causing
	      // stopPlayback to switch from false to
	      // true.
	      while((cnt = audioInputStream.read(
	           tempBuffer,0,tempBuffer.length)) != -1
	                       && stopPlayback == false){
	        if(cnt > 0){
	          //Write data to the internal buffer of
	          // the data line where it will be
	          // delivered to the speaker.
	          sourceDataLine.write(
	                             tempBuffer, 0, cnt);
	        }//end if
	      }//end while
	      //Block and wait for internal buffer of the
	      // data line to empty.
	      sourceDataLine.drain();
	      sourceDataLine.close();

	      //Prepare to playback another file
	      stopPlayback = false;
	    }catch (Exception e) {
	      e.printStackTrace();
	      System.exit(0);
	    }//end catch
	  }//end run
	}//end inner class PlayThread
	//===================================//
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
	    if (!source.getValueIsAdjusting()) {
	        int ms = (int)source.getValue();
	        timer = new javax.swing.Timer(ms, this);
	    }
		
	}

}

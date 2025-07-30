package com.Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Interface extends JFrame{
	final  JPanel panelBotones;
	final JButton botonElegirCarpeta, themeButton;
	
	public Interface(){
		setLayout(null); // Null para poder especificar X 
		// Panel con scroll para los botones
        panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelBotones);

        // Botón para elegir carpeta
        botonElegirCarpeta = new JButton("+");
        botonElegirCarpeta.addActionListener(e -> seleccionarExe());
        botonElegirCarpeta.setBackground(Color.WHITE);
        botonElegirCarpeta.setBounds(1050,15,20,20);
        botonElegirCarpeta.setBorder(null);
        
        themeButton = new JButton("Theme");
        themeButton.addActionListener(this::ChangeTheme);
        themeButton.setBackground(Color.WHITE);
        themeButton.setBorder(null);
        themeButton.setBounds(1099,10,80,30);
        // Añadir botones (LO PODRIA HABER PUESTO DEBAJO DE CADA BOTON :3, PERO ME DA PEREZA)
        setLayout(new BorderLayout());
        add(botonElegirCarpeta);
        add(themeButton);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
	}

	public void ChangeTheme(ActionEvent e) {
		Color getBackground = getContentPane().getBackground();		
        if(getBackground == Color.WHITE){
            getContentPane().setBackground(Color.DARK_GRAY);
            themeButton.setBackground(Color.DARK_GRAY);
            themeButton.setForeground(Color.WHITE);
            botonElegirCarpeta.setBackground(Color.DARK_GRAY);
            botonElegirCarpeta.setForeground(Color.WHITE);
            panelBotones.setBackground(Color.DARK_GRAY);
            panelBotones.setForeground(Color.WHITE);
        } else {
        	getContentPane().setBackground(Color.WHITE);
            themeButton.setBackground(Color.WHITE);
            themeButton.setForeground(Color.BLACK);
            botonElegirCarpeta.setBackground(Color.WHITE);
            botonElegirCarpeta.setForeground(Color.DARK_GRAY);
            panelBotones.setBackground(Color.WHITE);
            panelBotones.setForeground(Color.BLACK);
        }
	}

	 private void seleccionarExe() {
	        JFileChooser fileChooser = new JFileChooser();
	        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	        fileChooser.setMultiSelectionEnabled(true); // Seleccionar varios .exe
	        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
	        	@Override
	        	public boolean accept(File f) {
	        		return f.isDirectory() || f.getName().toLowerCase().endsWith(".exe");			
 	        	}
	        	
	        	@Override
	        	public String getDescription() {
	        		return "Archivos ejecutables (*.exe)";
	        	}
	        });
	        
	        int resultado = fileChooser.showOpenDialog(this);
	        if (resultado == JFileChooser.APPROVE_OPTION) {
	        	File[] archivosSeleccionados = fileChooser.getSelectedFiles();
	        	if (archivosSeleccionados.length == 0) {
	        		archivosSeleccionados = new File[] {fileChooser.getSelectedFile()};
	        	}
	        	mostrarExeSeleccionados(archivosSeleccionados);
	        }
	    }

	 
	 private void mostrarExeSeleccionados(File[] archivosExe){  
		 for (File archivo : archivosExe) {
			 JButton boton = new JButton(archivo.getName());
			 boton.setAlignmentX(Component.LEFT_ALIGNMENT); // Centrar el botón
			 boton.addActionListener(e -> ejecutarExe(archivo));
			 panelBotones.add(Box.createVerticalStrut(10)); // Espaciado
			 panelBotones.add(boton);
		 }
        panelBotones.revalidate(); // Refrescar el panel
        panelBotones.repaint();
	}
	
	private void ejecutarExe(File archivo) {
        try {
            Runtime.getRuntime().exec(archivo.getAbsolutePath()); // Funciona XD
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al ejecutar: " + archivo.getName());
            e.printStackTrace();
        }
    }

}

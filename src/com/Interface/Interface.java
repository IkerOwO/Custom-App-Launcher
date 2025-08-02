package com.Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Interface extends JFrame{
	final  JPanel panelBotones;
	final JButton botonElegirCarpeta, themeButton;
	
	public Interface(){
		// Panel con scroll para los botones
        panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panelBotones); // Equivalente a un margin left de 20px
       
        // Barra para los botones de Theme y Agregar
        //JPanel barraSuperior = new JPanel(null); // usamos null aquí para colocar libremente
        //barraSuperior.setPreferredSize(new Dimension(1200, 60));        
        
        // Botón para elegir exe
        botonElegirCarpeta = new JButton("+");
        botonElegirCarpeta.addActionListener(e -> seleccionarExe());
        botonElegirCarpeta.setBackground(Color.WHITE);
        botonElegirCarpeta.setBounds(1110,5,50,50);
        
        themeButton = new JButton("Theme");
        themeButton.addActionListener(this::ChangeTheme);
        themeButton.setBackground(Color.WHITE);
        themeButton.setBounds(10,10,80,30);
        // Añadir botones (LO PODRIA HABER PUESTO DEBAJO DE CADA BOTON :3, PERO ME DA PEREZA)
        setLayout(new BorderLayout());
        add(botonElegirCarpeta);
        add(themeButton);
        add(scrollPane, BorderLayout.CENTER);
        //add(barraSuperior);
        setVisible(true);
	}

	public void ChangeTheme(ActionEvent e) {
		Color getBackground = getContentPane().getBackground();		
        if(getBackground == Color.WHITE){
            getContentPane().setBackground(Color.DARK_GRAY);
            themeButton.setBackground(Color.DARK_GRAY);
            themeButton.setForeground(Color.WHITE);
            themeButton.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            
            botonElegirCarpeta.setBackground(Color.DARK_GRAY);
            botonElegirCarpeta.setForeground(Color.WHITE);
            botonElegirCarpeta.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            
            panelBotones.setBackground(Color.DARK_GRAY);
            panelBotones.setForeground(Color.WHITE);
            
        } else {
        	getContentPane().setBackground(Color.WHITE);
            themeButton.setBackground(Color.WHITE);
            themeButton.setForeground(Color.BLACK);
            themeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            botonElegirCarpeta.setBackground(Color.WHITE);
            botonElegirCarpeta.setForeground(Color.DARK_GRAY);
            botonElegirCarpeta.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
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
		     boton.setBackground(Color.WHITE);
		     boton.setForeground(Color.BLACK);
			 panelBotones.add(Box.createVerticalStrut(50), Box.createHorizontalStrut(10)); // Espaciado
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

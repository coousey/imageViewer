package com.starterkit.javafx.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.starterkit.javafx.model.ImageViewer;
import com.starterkit.javafx.model.PersonSearch;
import com.sun.javafx.logging.Logger;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ImageViewerController {

	@FXML
	private TextField pathField;

	@FXML
	private Button searchButton;

	@FXML
	private ImageView myImageView;
	

	private final ImageViewer model = new ImageViewer();

	@FXML
	private void initialize() {
		
		
		 System.out.println(myImageView);

		pathField.textProperty().bindBidirectional(model.pathProperty());
	
//	    myImageView.setImage(new Image("src/main/resources/com/starterkit/javafx/img1.jpg"));
	        
//		BufferedImage bufferedImage;
//		try {
//			File bla = new File("src/main/resources/com/starterkit/javafx/img1.jpg");
//			System.out.println(bla.getCanonicalPath());
//			
//			bufferedImage = ImageIO.read(bla);
//			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//			myImageView.setImage(image);
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}
			
		File file = new File("src/main/resources/com/starterkit/javafx/img1.jpg");
        Image image = new Image(file.toURI().toString());
        myImageView.setImage(image);
		
		
	}

	@FXML
	private void searchButtonAction(ActionEvent event) {

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

		// Show open file dialog
		File file = fileChooser.showOpenDialog(null);

		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			myImageView.setImage(image);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

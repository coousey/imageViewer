
package com.starterkit.javafx.controller;

import java.io.File;
import java.util.ArrayList;

import com.starterkit.javafx.model.ImageViewer;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

public class ImageViewerController {

	@FXML
	private VBox listBox;

	@FXML
	private TextField pathField;
	
	@FXML
	private Button nextButton;
	@FXML
	private Button previousButton;
	@FXML
	private Button plusButton;
	@FXML
	private Button minusButton;
	@FXML
	private Button chooseDirectoryButton;

	@FXML
	private Button slideShowButton;

	@FXML
	private ScrollPane scrollPane;
	@FXML
	private ImageView imageView;

	@FXML
	private ListView<String> list;
	
	private Thread backgroundThread;

	private final ImageViewer model = new ImageViewer();

	private double xScale;
	private double yScale;
	// REV: powinienes zaladowac pliki przez classloadera
	private String infoImagesPath = "file:///C:\\projects\\starterkit\\todo-jee\\workspacefx\\javafx\\src\\main\\resources\\com\\starterkit\\javafx\\";
	
	@FXML
	private void initialize() {

		pathField.textProperty().bindBidirectional(model.pathProperty());

		list.itemsProperty().bind(model.imagesProperty());

		pathField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> o, String old, String n) {
				File file = new File(n);
				if (file != null && file.isDirectory()) {	
					findFiles(model.getPath());
				} else  {
					imageView.setImage(new Image(infoImagesPath + "notADir.jpg"));
				}
			}
		});

		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> o, String old, String n) {
				Image image = new Image("file:///" + n);
				imageView.setImage(image);
				updateScale();
			}
		});
		
		list.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

			@Override
			public ListCell<String> call(ListView<String> param) {
				ListCell<String> cell = new ListCell<String>() {

					@Override
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty) {
							String name = null;
							int i = item.lastIndexOf('\\');
							if (i > 0) {
								name = item.substring(i + 1);
							}

							this.setText(name);
						}
						else {
							this.setText(null);
						}
					}
				};
				return cell;
			}
		});
		model.setPath(System.getProperty("user.home"));
	}

	@FXML
	private void slideShowButtonAction(ActionEvent event) {
		stopSlideShow();
		list.getSelectionModel().clearAndSelect(0);

		Task<Void> task = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				for (int i = 0; i < model.getImages().size(); i++) {
					Thread.sleep(3000);
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							list.getSelectionModel().selectNext();
							updateScale();
						}
					});
				}
				return null;
			}
		};
		backgroundThread = new Thread(task);
		backgroundThread.setDaemon(true);
		backgroundThread.start();
	}
	
	@FXML
	private void chooseDirectoryButtonAction(ActionEvent event) {
		stopSlideShow();
		
		DirectoryChooser directoryChooser = new DirectoryChooser();
		// REV: okno powinno byc modalne
		File file = directoryChooser.showDialog(null);
		if (file != null && file.isDirectory()) {
			model.setPath(file.getPath());
		}
	}

	private void findFiles(String path) {
		File file = new File(path);

		if (file != null) {

			String extension = "";
			ArrayList<String> images = new ArrayList<String>();

			// REV: lepiej uzyc filtra
			for (final File fileEntry : file.listFiles()) {

				int i = fileEntry.getAbsolutePath().lastIndexOf('.');
				if (i > 0) {
					extension = fileEntry.getAbsolutePath().substring(i + 1);
				}

				if (!fileEntry.isDirectory() && (extension.equals("jpg") || extension.equals("png"))) {
					images.add(fileEntry.getAbsolutePath());
				}
			}
			
			model.setImages(images);

			if(images.size() == 0){
				imageView.setImage(new Image(infoImagesPath + "noImages.jpg"));
			}
			else{
				imageView.setImage(new Image(infoImagesPath + "noSelected.jpg"));
			}
		}
	}
	
	private void updateScale() {
		imageView.setFitHeight(scrollPane.getHeight() - 2);
		imageView.setFitWidth(scrollPane.getWidth() - 2);
		xScale = imageView.getFitWidth();
		yScale = imageView.getFitHeight();
	}
	
	@FXML
	private void nextButtonAction(ActionEvent event) {
		stopSlideShow();
		
		list.getSelectionModel().selectNext();
		updateScale();
	}

	@FXML
	private void previousButtonAction(ActionEvent event) {
		stopSlideShow();
		
		list.getSelectionModel().selectPrevious();
		updateScale();
	}

	@FXML
	private void plusButtonAction(ActionEvent event) {
		stopSlideShow();
		
		xScale *= 1.5;
		yScale *= 1.5;
		imageView.setFitWidth(xScale);
		imageView.setFitHeight(yScale);
	}

	@FXML
	private void minusButtonAction(ActionEvent event) {
		stopSlideShow();
		
		xScale *= 0.66666;
		yScale *= 0.66666;
		imageView.setFitWidth(xScale);
		imageView.setFitHeight(yScale);
	}
	
	private void stopSlideShow(){
		if(backgroundThread != null && backgroundThread.isAlive()){
			// REV: jak to dziala? :-)
			backgroundThread.interrupt();
		}
	}
}
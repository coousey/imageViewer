package com.starterkit.javafx.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.image.Image;

public class ImageViewer {

	private final StringProperty path = new SimpleStringProperty();
	private final ObjectProperty<Image> image = new SimpleObjectProperty<>();
	private final ListProperty<String> images = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
	
	
	public final String getPath() {
		return path.get();
	}
	public final void setPath(String path){
		this.path.set(path);
	}
	public StringProperty pathProperty() {
		return path;
	}
	
	
	public final Image getImage() {
		return image.get();
	}
	public final void setImage(Image image){
		this.image.set(image);
	}
	public ObjectProperty<Image> imageProperty(){
		return image;
	}
	
	
	public final List<String> getImages() {
		return images.get();
	}
	public final void setResult(List<String> images) {
		this.images.setAll(images);
	}
	public ListProperty<String> imagesProperty() {
		return images;
	}
	
}
 
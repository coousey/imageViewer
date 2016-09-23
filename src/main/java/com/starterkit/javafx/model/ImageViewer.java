package com.starterkit.javafx.model;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class ImageViewer {

	private final StringProperty path = new SimpleStringProperty();
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
	
	public final List<String> getImages() {
		return images.get();
	}
	public final void setImages(List<String> images) {
		this.images.setAll(images);
	}
	public ListProperty<String> imagesProperty() {
		return images;
	}
	
}
 
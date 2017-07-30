package ui.general;

/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 */


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import util.Tuple;


/**
 *
 * @author akouznet
 */
public class ImageViewPane extends Region {
    
    private ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<ImageView>();

    public ObjectProperty<ImageView> imageViewProperty() {
        return imageViewProperty;
    }
    
    public ImageView getImageView() {
        return imageViewProperty.get();
    }
    
    public void setImageView(ImageView imageView) {
        this.imageViewProperty.set(imageView);
    }

    public ImageViewPane() {
        this(new ImageView());
    }

    @Override
    protected void layoutChildren() {
        ImageView imageView = imageViewProperty.get();
        if (imageView != null) {
            imageView.setFitWidth(getWidth());
            imageView.setFitHeight(getHeight());
            layoutInArea(imageView, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
        }
        super.layoutChildren();
    }
    
    /**
     * 
     * @return the width inset and height inset of the imageview in relation to its parent node 
     */
    public Tuple<Double, Double> getImageInsets(){
    	double imageNaturalWidth = this.imageViewProperty.get().getImage().getWidth();
    	double imageNaturalHeight = this.imageViewProperty.get().getImage().getHeight();
    	double width = this.getWidth();
    	double height = this.getHeight();
    	//this boolean is true if the vertical inset is zero
    	boolean b = (imageNaturalWidth/imageNaturalHeight > width/height); 
    	double wInset = b? 0: 0.5*(width - imageNaturalWidth*height/imageNaturalHeight);
    	double hInset = b? 0.5*(height - imageNaturalHeight*width/imageNaturalWidth) : 0;
    	
    	return new Tuple<Double, Double>(wInset,hInset);
    }

    
    public ImageViewPane(ImageView imageView) {
    	imageView.setPreserveRatio(true);
        imageViewProperty.addListener(new ChangeListener<ImageView>() {

            @Override
            public void changed(ObservableValue<? extends ImageView> arg0, ImageView oldIV, ImageView newIV) {
                if (oldIV != null) {
                    getChildren().remove(oldIV);
                }
                if (newIV != null) {
                    getChildren().add(newIV);
                }
            }
        });
        this.imageViewProperty.set(imageView);
    }
}
package Entropy.engine.entities;

import Entropy.engine.models.TexturedModel;

public class Entity {
    private TexturedModel model;
    private Transform transform;

    public Entity(TexturedModel model) {
        this.model = model;
        this.transform = new Transform();
    }

    public Entity(TexturedModel model, Transform transform) {
        this.model = model;
        this.transform = transform;
    }

    public TexturedModel getModel() {
        return model;
    }

    public Transform getTransform() {
        return transform;
    }
}

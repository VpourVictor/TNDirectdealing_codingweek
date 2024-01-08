module eu.telecomnancy.labfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires com.fasterxml.jackson.databind;

    opens eu.telecomnancy.labfx to javafx.fxml;
    exports eu.telecomnancy.labfx;
    opens eu.telecomnancy.labfx.controller to javafx.fxml;
    exports eu.telecomnancy.labfx.controller;
    opens eu.telecomnancy.labfx.model to javafx.fxml;
    exports eu.telecomnancy.labfx.model;
    opens eu.telecomnancy.labfx.controller.posts to javafx.fxml;
    exports eu.telecomnancy.labfx.controller.posts;
}
module eu.telecomnancy.labfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.json;

    opens eu.telecomnancy.labfx to javafx.fxml;
    exports eu.telecomnancy.labfx;
    opens eu.telecomnancy.labfx.controller to javafx.fxml;
    exports eu.telecomnancy.labfx.controller;
    opens eu.telecomnancy.labfx.model to javafx.fxml;
    exports eu.telecomnancy.labfx.model;
    opens eu.telecomnancy.labfx.controller.posts to javafx.fxml;
    exports eu.telecomnancy.labfx.controller.posts;
    exports eu.telecomnancy.labfx.controller.utils;
    opens eu.telecomnancy.labfx.controller.utils to javafx.fxml;
}
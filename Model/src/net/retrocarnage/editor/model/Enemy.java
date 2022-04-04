package net.retrocarnage.editor.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Enemy is the serialized definition of an enemy.
 *
 * @author Thomas Werner
 */
public class Enemy implements Selectable, Transferable {

    public static final DataFlavor DATA_FLAVOR = new DataFlavor(Enemy.class, "enemy");
    public static final int LANDMINE_HEIGHT = 44;
    public static final int LANDMINE_WIDTH = 50;
    public static final int PERSON_HEIGHT = 150;
    public static final int PERSON_WIDTH = 90;

    private static final Logger logger = Logger.getLogger(Enemy.class.getName());

    private List<EnemyMovement> movements;
    private String direction;
    private Position position;
    private String skin;
    private int type;
    private List<EnemyAction> actions;

    public Enemy() {
        this.movements = new ArrayList<>();
        this.actions = new ArrayList<>();
    }

    public List<EnemyMovement> getMovements() {
        return movements;
    }

    public void setMovements(final List<EnemyMovement> movements) {
        this.movements = movements;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(final String direction) {
        this.direction = direction;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(final Position position) {
        this.position = position;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(final String skin) {
        this.skin = skin;
    }

    public int getType() {
        return type;
    }

    public void setType(final int type) {
        this.type = type;
    }

    public List<EnemyAction> getActions() {
        return actions;
    }

    public void setActions(final List<EnemyAction> actions) {
        this.actions = actions;
    }

    @JsonIgnore
    @Override
    public boolean isMovable() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isResizable() {
        return false;
    }

    @JsonIgnore
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DATA_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(final DataFlavor df) {
        return DATA_FLAVOR.equals(df);
    }

    @Override
    public Object getTransferData(final DataFlavor df) throws UnsupportedFlavorException, IOException {
        if (DATA_FLAVOR.equals(df)) {
            return this;
        }
        throw new UnsupportedFlavorException(df);
    }

    /**
     * Creates a deep copy of this object.
     *
     * @return the copy
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Enemy clone() throws CloneNotSupportedException {
        try {
            final ObjectMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(xmlMapper.writeValueAsString(this), Enemy.class);
        } catch (JsonProcessingException ex) {
            logger.log(Level.SEVERE, "Failed to serialize / deserialize Enemy instance", ex);
            throw new IllegalArgumentException("Enemy can't be serialized / deserialized", ex);
        }
    }

}

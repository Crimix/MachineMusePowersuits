package net.machinemuse.powersuits.gui.tinker.scrollable;

import net.machinemuse.numina.utils.math.geometry.MusePoint2D;
import net.machinemuse.numina.utils.math.geometry.MuseRelativeRect;
import net.machinemuse.powersuits.gui.tinker.clickable.ClickableLabel;

public class ScrollableLabel extends ScrollableRectangle {
    ClickableLabel label;


    public ScrollableLabel(ClickableLabel label, MuseRelativeRect relativeRect) {
        super(relativeRect);
        this.label = label;
    }

    public ScrollableLabel(ClickableLabel label, double left, double top, double right, double bottom) {
        super(left, top, right, bottom);
        this.label = label;
    }

    public ScrollableLabel(ClickableLabel label, double left, double top, double right, double bottom, boolean growFromMiddle) {
        super(left, top, right, bottom, growFromMiddle);
        this.label = label;
    }

    public ScrollableLabel(ClickableLabel label, MusePoint2D ul, MusePoint2D br) {
        super(ul, br);
        this.label = label;
    }

    public void setText(String text) {
        label.setLabel(text);
    }

    public boolean hitbox(double x, double y) {
        return label.hitBox(x, y);
    }

    @Override
    public void draw() {
        label.draw();
    }
}
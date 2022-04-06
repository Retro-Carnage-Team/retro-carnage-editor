package net.retrocarnage.editor.renderer.common;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.List;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.renderer.SectionAnalysis;
import net.retrocarnage.editor.renderer.SectionPathRunner;

/**
 * Builds a Shape that matches the area of the mission.
 *
 * @author Thomas Werner
 */
public class ClipShapeFactory {

    private final SectionAnalysis sectionAnalysis;
    private final List<Section> sections;
    private final int gameScreenWidth;

    public ClipShapeFactory(
            final SectionAnalysis sectionAnalysis,
            final List<Section> sections,
            final int gameScreenWidth) {
        this.sectionAnalysis = sectionAnalysis;
        this.sections = sections;
        this.gameScreenWidth = gameScreenWidth;
    }

    public Area build() {
        return new ShapeBuilder(sectionAnalysis, sections, gameScreenWidth).build();
    }

    private static class ShapeBuilder extends SectionPathRunner {

        private Area result = null;

        public ShapeBuilder(final SectionAnalysis sa, final List<Section> list, final int i) {
            super(sa, list, i);
        }

        @Override
        protected void processSectionRect(final Section section, final int x, final int y, final int w, final int h) {
            final Rectangle sectionRect = new Rectangle(x, y, w, h);
            if (null == result) {
                result = new Area(sectionRect);
            } else {
                result.add(new Area(sectionRect));
            }
        }

        public Area build() {
            run();
            return result;
        }
    }

}

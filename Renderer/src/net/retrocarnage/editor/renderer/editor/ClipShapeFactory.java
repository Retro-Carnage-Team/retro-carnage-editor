package net.retrocarnage.editor.renderer.editor;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.List;
import net.retrocarnage.editor.model.Section;
import net.retrocarnage.editor.renderer.SectionAnalyzer;

/**
 * Builds a Shape that matches the area of the mission.
 *
 * @author Thomas Werner
 */
class ClipShapeFactory {

    private final SectionAnalyzer.SectionAnalysis sectionAnalysis;
    private final List<Section> sections;
    private final int gameScreenWidth;

    public ClipShapeFactory(
            final SectionAnalyzer.SectionAnalysis sectionAnalysis,
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

        public ShapeBuilder(final SectionAnalyzer.SectionAnalysis sa, final List<Section> list, final int i) {
            super(sa, list, i);
        }

        @Override
        protected void processSectionRect(final int x, final int y, final int w, final int h) {
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

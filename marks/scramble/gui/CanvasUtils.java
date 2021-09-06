package marks.scramble.gui;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

public class CanvasUtils {
    private static double referenceWidth;
    private static double referenceHeight;

    public CanvasUtils() {
    }

    public static void setReferences(double refW, double refH) {
        referenceWidth = refW;
        referenceHeight = refH;
    }

    public static double getReferenceWidth() {
        return referenceWidth;
    }

    public static double getReferenceHeight() {
        return referenceHeight;
    }

    public static double getWindowX(double canvasX) {
        return canvasX * GUIHandler.stageW / referenceWidth;
    }

    public static double getWindowY(double canvasY) {
        return canvasY * GUIHandler.stageH / referenceHeight;
    }

    public static double getReferenceX(double windowX) {
        return windowX / GUIHandler.stageW * referenceWidth;
    }

    public static double getReferenceY(double windowY) {
        return windowY / GUIHandler.stageH * referenceHeight;
    }

    public static void drawText(double x, double y, double height, String text, Color c) {
        double adjX = x * GUIHandler.stageW / referenceWidth;
        double adjH = height * GUIHandler.stageH / referenceHeight;
        double adjY = y * GUIHandler.stageH / referenceHeight + adjH;
        GUIHandler.gc.setFill(c);
        GUIHandler.gc.setFont(Font.font("Copperplate Gothic Bold", 4.0D * adjH / 3.0D + 2.0D));
        GUIHandler.gc.setTextAlign(TextAlignment.LEFT);
        GUIHandler.gc.fillText(text, adjX, adjY);
    }

    public static void drawTextCentered(double x, double y, double height, String text, Color c) {
        double adjX = x * GUIHandler.stageW / referenceWidth;
        double adjH = height * GUIHandler.stageH / referenceHeight;
        double adjY = y * GUIHandler.stageH / referenceHeight + adjH / 2.0D;
        GUIHandler.gc.setFill(c);
        GUIHandler.gc.setFont(Font.font("Copperplate Gothic Bold", 4.0D * adjH / 3.0D + 2.0D));
        GUIHandler.gc.setTextAlign(TextAlignment.CENTER);
        GUIHandler.gc.fillText(text, adjX, adjY);
    }

    public static void drawTextRotated(double x, double y, double height, String text, Color c, double angle) {
        double adjX = x * GUIHandler.stageW / referenceWidth;
        double adjH = height * GUIHandler.stageH / referenceHeight;
        double adjY = y * GUIHandler.stageH / referenceHeight + Math.cos(angle) * adjH / 2.0D;
        GUIHandler.gc.save();
        Rotate r = new Rotate(angle * -180.0D / 3.141592653589793D, adjX, adjY);
        GUIHandler.gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        GUIHandler.gc.setFill(c);
        GUIHandler.gc.setFont(Font.font("Copperplate Gothic Bold", 4.0D * adjH / 3.0D + 2.0D));
        GUIHandler.gc.setTextAlign(TextAlignment.CENTER);
        GUIHandler.gc.fillText(text, adjX, adjY);
        GUIHandler.gc.restore();
    }

    public static void drawRect1(double x1, double y1, double x2, double y2, Color c) {
        double adjX;
        if (x1 > x2) {
            adjX = x1;
            x1 = x2;
            x2 = adjX;
        }

        if (y1 > y2) {
            adjX = y1;
            y1 = y2;
            y2 = adjX;
        }

        GUIHandler.gc.setFill(c);
        adjX = x1 * GUIHandler.stageW / referenceWidth;
        double adjY = y1 * GUIHandler.stageH / referenceHeight;
        GUIHandler.gc.fillRect(adjX, adjY, x2 * GUIHandler.stageW / referenceWidth - adjX, y2 * GUIHandler.stageH / referenceHeight - adjY);
    }

    public static void drawRect2(double x, double y, double w, double h, Color c) {
        GUIHandler.gc.setFill(c);
        GUIHandler.gc.fillRect(x * GUIHandler.stageW / referenceWidth, y * GUIHandler.stageH / referenceHeight, w * GUIHandler.stageW / referenceWidth, h * GUIHandler.stageH / referenceHeight);
    }

    public static void drawRectOutline2(double x, double y, double w, double h, Color c, double strokeSize) {
        GUIHandler.gc.setStroke(c);
        GUIHandler.gc.setLineWidth(strokeSize);
        GUIHandler.gc.strokeRect(x * GUIHandler.stageW / referenceWidth, y * GUIHandler.stageH / referenceHeight, w * GUIHandler.stageW / referenceWidth, h * GUIHandler.stageH / referenceHeight);
        GUIHandler.gc.setStroke((Paint)null);
    }

    public static void drawLine(double x1, double y1, double x2, double y2, double width, Color c) {
        GUIHandler.gc.setStroke(c);
        GUIHandler.gc.setLineWidth(width * GUIHandler.stageH / referenceHeight);
        GUIHandler.gc.strokeLine(x1 * GUIHandler.stageW / referenceWidth, y1 * GUIHandler.stageH / referenceHeight, x2 * GUIHandler.stageW / referenceWidth, y2 * GUIHandler.stageH / referenceHeight);
    }

    public static void clear() {
        GUIHandler.gc.clearRect(0.0D, 0.0D, GUIHandler.stageW, GUIHandler.stageH);
    }

    public static void drawImage1(Image img, double x1, double y1, double x2, double y2) {
        double adjX = x1 * GUIHandler.stageW / referenceWidth;
        double adjY = y1 * GUIHandler.stageH / referenceHeight;
        GUIHandler.gc.drawImage(img, adjX, adjY, x2 * GUIHandler.stageW / referenceWidth - adjX, y2 * GUIHandler.stageH / referenceHeight - adjY);
    }

    public static void drawImage2(Image img, double x, double y, double w, double h) {
        GUIHandler.gc.drawImage(img, x * GUIHandler.stageW / referenceWidth, y * GUIHandler.stageH / referenceHeight, w * GUIHandler.stageW / referenceWidth, h * GUIHandler.stageH / referenceHeight);
    }

    public static void drawImage3(Image img, double centerX, double centerY, double w, double h) {
        double adjW = w * GUIHandler.stageW / referenceWidth;
        double adjH = h * GUIHandler.stageH / referenceHeight;
        GUIHandler.gc.drawImage(img, centerX * GUIHandler.stageW / referenceWidth - adjW / 2.0D, centerY * GUIHandler.stageH / referenceHeight - adjH / 2.0D, adjW, adjH);
    }

    public static void drawImageRotated(Image img, double centerX, double centerY, double w, double h, double angle) {
        double adjW = w * GUIHandler.stageW / referenceWidth;
        double adjH = h * GUIHandler.stageH / referenceHeight;
        double adjX = centerX * GUIHandler.stageW / referenceWidth;
        double adjY = centerY * GUIHandler.stageH / referenceHeight;
        GUIHandler.gc.save();
        Rotate r = new Rotate(angle * -180.0D / 3.141592653589793D, adjX, adjY);
        GUIHandler.gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
        GUIHandler.gc.drawImage(img, adjX - adjW / 2.0D, adjY - adjH / 2.0D, adjW, adjH);
        GUIHandler.gc.restore();
    }

    public static void drawPolygon(Color c, double... points) {
        GUIHandler.gc.setFill(c);
        double[] xs = new double[points.length / 2];
        double[] ys = new double[points.length / 2];

        for(int i = 0; i < points.length; i += 2) {
            xs[i / 2] = points[i] * GUIHandler.stageW / referenceWidth;
            ys[i / 2] = points[i + 1] * GUIHandler.stageH / referenceHeight;
        }

        GUIHandler.gc.fillPolygon(xs, ys, xs.length);
    }

    public static void drawOvalOutline(Color c, double centerX, double centerY, double width, double height, double strokeSize) {
        double adjW = width * GUIHandler.stageW / referenceWidth;
        double adjH = height * GUIHandler.stageH / referenceHeight;
        double adjX = centerX * GUIHandler.stageW / referenceWidth;
        double adjY = centerY * GUIHandler.stageH / referenceHeight;
        GUIHandler.gc.setStroke(c);
        GUIHandler.gc.setLineWidth(strokeSize);
        GUIHandler.gc.strokeOval(adjX - adjW / 2.0D, adjY - adjH / 2.0D, adjW, adjH);
        GUIHandler.gc.setStroke((Paint)null);
    }

    public static void drawOval(Color c, double centerX, double centerY, double width, double height) {
        double adjW = width * GUIHandler.stageW / referenceWidth;
        double adjH = height * GUIHandler.stageH / referenceHeight;
        double adjX = centerX * GUIHandler.stageW / referenceWidth;
        double adjY = centerY * GUIHandler.stageH / referenceHeight;
        GUIHandler.gc.setFill(c);
        GUIHandler.gc.fillOval(adjX - adjW / 2.0D, adjY - adjH / 2.0D, adjW, adjH);
    }

    public static void drawCircleOutline(Color c, double centerX, double centerY, double radius, double strokeSize) {
        drawOvalOutline(c, centerX, centerY, radius * 2.0D, radius * 2.0D, strokeSize);
    }

    public static void drawCircle(Color c, double centerX, double centerY, double radius) {
        drawOval(c, centerX, centerY, radius * 2.0D, radius * 2.0D);
    }

    public static void drawText2(Font font, String text, Color c, double x, double y, double height) {
        double adjX = x * GUIHandler.stageW / referenceWidth;
        double adjH = height * GUIHandler.stageH / referenceHeight;
        double adjY = y * GUIHandler.stageH / referenceHeight + adjH * 3.0D / 4.0D;
        GUIHandler.gc.setFont(Font.font(font.getName(), adjH * 3.0D / 4.0D));
        GUIHandler.gc.setFill(c);
        GUIHandler.gc.fillText(text, adjX, adjY);
    }
}

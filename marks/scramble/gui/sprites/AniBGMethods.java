package marks.scramble.gui.sprites;

import java.util.Random;
import java.util.function.Consumer;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import marks.scramble.game.Level;
import marks.scramble.gui.CanvasUtils;
import marks.scramble.gui.resources.Images;

class AniBGMethods {
    static final double[] RANDOMS = new double[]{0.0D, 0.5D, 0.2D, 0.6D, 0.7D, 0.1D, 0.4D, 0.4D, 0.9D, 0.5D, 0.2D, 0.11D, 0.2D, 0.6D, 0.8D, 0.9D, 0.1D, 0.9D, 0.9D, 0.3D, 0.0D, 0.0D, 0.0D, 0.3D, 0.1D, 0.8D, 0.7D, 0.3D, 0.4D, 0.1D, 0.9D, 0.53D, 0.2D, 0.9D, 0.4D, 0.5D, 0.9D, 0.1D, 0.3D, 0.7D};
    static final int[] DBSE = new int[]{19350, 28950, 38500, 57748, 76988};
    static final int[] FP = new int[]{0, 55000, 80000, 90000, 98000, 104000, 108000, 110000, 130000};
    static int counter;

    AniBGMethods() {
    }

    public static Consumer<Integer> getMethod(int id) {
        switch(id) {
        case 1:
            return AniBGMethods::BG1;
        case 2:
            return AniBGMethods::BG2;
        case 3:
            return AniBGMethods::BG3;
        case 4:
            return AniBGMethods::BG4;
        case 5:
            return AniBGMethods::BG5;
        case 6:
            return AniBGMethods::BG6;
        case 7:
            return AniBGMethods::BG7;
        case 8:
            return AniBGMethods::BG8;
        case 9:
            return AniBGMethods::BG9;
        case 10:
            return AniBGMethods::BG10;
        default:
            System.out.println("ERROR: AniBGMethods getMethod not passed proper id!");
            return null;
        }
    }

    public static void BG1(int tt) {
        int flash = Math.min(Math.min(tt % 600, Math.min(tt % 2600, tt % 9400)), 200);
        CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(255, 255, 255, (double)(200 - flash) / 755.0D));
    }

    public static void BG2(int totalTime) {
        if (totalTime >= 46220) {
            CanvasUtils.drawImage2(Images.get("extra1.png"), 0.0D, 0.0D, 1200.0D, 800.0D);
        }

        double SIZE = 80.0D;

        int i;
        double startX;
        double startY;
        double progress;
        double snowX;
        double snowY;
        for(i = 0; i < 5; ++i) {
            startX = (double)((366 * i + 933) % 1200 - 1200);
            startY = -40.0D - 320.0D * (double)i;
            progress = (double)totalTime / 1000.0D * (500.0D + RANDOMS[i] * 79.0D);
            snowX = 1200.0D + (startX - progress) % 1280.0D - 40.0D;
            snowY = -80.0D;
            if (startY + progress > -40.0D) {
                snowY = (startY + progress) % 880.0D - 40.0D;
            }

            CanvasUtils.drawImageRotated(totalTime >= 46220 ? Images.get("extra2.png") : Images.get("extra3.png"), snowX, snowY, 80.0D, 80.0D, progress / 350.0D * 3.141592653589793D + (double)i * 0.15D);
        }

        if (totalTime > 60980) {
            totalTime -= 60980;

            for(i = 5; i < 40; ++i) {
                startX = (double)((366 * i + 933) % 1200 - 1200);
                startY = -40.0D - 80.0D * (double)i;
                progress = (double)totalTime / 1000.0D * (500.0D + RANDOMS[i] * 79.0D);
                snowX = 1200.0D + (startX - progress) % 1280.0D - 40.0D;
                snowY = -80.0D;
                if (startY + progress > -40.0D) {
                    snowY = (startY + progress) % 880.0D - 40.0D;
                }

                CanvasUtils.drawImageRotated(Images.get("extra2.png"), snowX, snowY, 80.0D, 80.0D, progress / 350.0D * 3.141592653589793D + (double)i * 0.15D);
            }
        }

    }

    public static void BG3(int totalTime) {
        double SIZE = 100.0D;
        double[] SPEEDS = new double[]{0.08D, 0.1D, 0.16D, 0.1D, 0.1D, 0.17777777777777778D, 0.1111111111111111D, 0.16D, 0.13333333333333333D, 0.09302325581395349D, 0.08D, 0.07272727272727272D, 0.08888888888888889D, 0.26666666666666666D};
        int numBalloons = totalTime < 94326 ? 6 : 36;

        int i;
        double x1;
        double x2;
        double y1;
        for(i = 0; i < numBalloons; ++i) {
            x1 = (double)((int)((double)totalTime / (900.0D / SPEEDS[i % SPEEDS.length])));
            x2 = ((double)(171 + 171 * i) + (double)(117 * i) * x1 * x1) % 1200.0D;
            y1 = 850.0D - (double)totalTime * SPEEDS[i % SPEEDS.length] % 900.0D;
            Image img = i % 3 == 0 ? Images.get("extra5.png") : (i % 3 == 1 ? Images.get("extra6.png") : Images.get("extra7.png"));
            CanvasUtils.drawImage3(img, x2, y1, 100.0D, 100.0D);
        }

        if (totalTime < 30850) {
            Level.SURPRISE.setBoxColor(Color.BLACK);
            Level.SURPRISE.setLetterColor(Color.WHITE);
        } else if (totalTime < 63290) {
            Level.SURPRISE.setBoxColor(Color.rgb(70, 0, 0));
            Level.SURPRISE.setLetterColor(Color.rgb(255, 200, 255));
            if (totalTime % 1200 < 100) {
                CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(255, 0, 0, 0.1D * (double)(1 - totalTime % 1200 / 100)));
            }

            if (totalTime % 2700 < 100) {
                CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(255, 255, 0, 0.1D * (double)(1 - totalTime % 2700 / 100)));
            }

            if (totalTime % 700 < 100) {
                CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(0, 255, 0, 0.1D * (double)(1 - totalTime % 700 / 100)));
            }
        } else if (totalTime < 63490) {
            double mult = 1.0D * (double)(totalTime - '\uf73a') / 200.0D;
            Level.SURPRISE.setBoxColor(Color.rgb(70 + (int)(185.0D * mult), (int)(255.0D * mult), (int)(255.0D * mult)));
            Level.SURPRISE.setLetterColor(Color.rgb((int)(255.0D * (1.0D - mult)), (int)(200.0D * (1.0D - mult)), (int)(255.0D * (1.0D - mult))));
            CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(0, 0, 0, mult));
        } else if (totalTime < 94326) {
            CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(0, 0, 0));

            for(i = 0; i < 7; ++i) {
                x1 = 1200.0D * RANDOMS[2 * i + 0];
                x2 = 1200.0D * RANDOMS[i + 1];
                y1 = 800.0D * RANDOMS[i + 2];
                double y2 = 1200.0D * RANDOMS[i + 3];
                double b = 3.141592653589793D / (3000.0D + 5000.0D * RANDOMS[i + 4]);
                double vMaxX = (x2 - x1) / 2.0D * b;
                double vMaxY = (y2 - y1) / 2.0D * b;
                double rad = 200.0D + 400.0D * RANDOMS[i + 5];
                CanvasUtils.drawCircleOutline(Color.WHITE, vMaxX / b * -Math.cos(b * (double)totalTime) + vMaxX / b + x1, vMaxY / b * -Math.cos(b * (double)totalTime) + vMaxY / b + y1, rad, 5.0D);
            }
        } else {
            Level.SURPRISE.setBoxColor(Color.rgb(0, 60, 60));
            Level.SURPRISE.setLetterColor(Color.rgb(255, 255, 200));
            if (totalTime % 1200 < 100) {
                CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(255, 0, 0, 0.1D * (double)(1 - totalTime % 1200 / 100)));
            }

            if (totalTime % 2700 < 100) {
                CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(255, 255, 0, 0.1D * (double)(1 - totalTime % 2700 / 100)));
            }

            if (totalTime % 700 < 100) {
                CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(0, 255, 0, 0.1D * (double)(1 - totalTime % 700 / 100)));
            }

            if (totalTime % 500 < 100) {
                CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(90, 90, 255, 0.1D * (double)(1 - totalTime % 500 / 100)));
            }
        }

    }

    public static void BG4(int totalTime) {
        Color bgColor = Color.YELLOW;
        Color lineColor = Color.RED;
        double thiccmulti = 1.0D;
        double angle = 25.0D;
        double MAG = 220.0D;
        double L1 = 150.0D;
        double L2 = 1450.0D;
        double cenX = 600.0D;
        double cenY = 400.0D;
        double mag = MAG * Math.cos((double)totalTime * 3.141592653589793D * 2.0D / 1300.0D);
        if (totalTime <= DBSE[0]) {
            bgColor = Color.rgb(255, 202, 68);
            lineColor = Color.rgb(255, 0, 0);
        } else if (totalTime <= DBSE[1]) {
            angle = 25.0D + 0.009D * (double)(totalTime - DBSE[0]);
            lineColor = Color.rgb(255, 202, 68);
            bgColor = Color.rgb(255, 0, 0);
        } else if (totalTime <= DBSE[2]) {
            angle = 111.4D + 0.036D * (double)(totalTime - DBSE[1]);
            lineColor = Color.rgb(255, 202, 68);
            bgColor = Color.rgb(255, 0, 0);
        } else if (totalTime <= DBSE[3]) {
            thiccmulti = 1.2D;
            mag *= 1.4D;
            angle = 455.2D;
            bgColor = Color.rgb(0, 255, 255);
            lineColor = Color.rgb(178, 0, 255);
        } else if (totalTime <= DBSE[4]) {
            thiccmulti = 1.1D;
            mag *= 1.2D;
            angle = 455.2D - 0.009D * (double)(totalTime - DBSE[3]);
            bgColor = Color.rgb(100, 0, 255);
            lineColor = Color.rgb(255, 202, 68);
        } else {
            angle = 196.72D;
            bgColor = Color.rgb(255, 202, 68);
            lineColor = Color.rgb(255, 0, 0);
        }

        angle *= -0.017453292519943295D;
        CanvasUtils.drawRect1(0.0D, 0.0D, 1200.0D, 800.0D, bgColor);

        int i;
        for(i = 0; i < 3; ++i) {
            double thickness = (double)(i == 1 ? 40 : 10) * thiccmulti;
            double h = Math.sqrt(mag * mag + L1 * L1);
            double ang2 = Math.atan2(mag, L1) + angle;
            double x0 = cenX - L2 * Math.cos(angle);
            double x1 = cenX - 2.0D * L1 * Math.cos(angle);
            double x2 = cenX - h * Math.cos(ang2);
            double x4 = cenX + h * Math.cos(ang2);
            double x5 = cenX + 2.0D * L1 * Math.cos(angle);
            double x6 = cenX + L2 * Math.cos(angle);
            double y0 = cenY - L2 * Math.sin(angle);
            double y1 = cenY - 2.0D * L1 * Math.sin(angle);
            double y2 = cenY - h * Math.sin(ang2);
            double y4 = cenY + h * Math.sin(ang2);
            double y5 = cenY + 2.0D * L1 * Math.sin(angle);
            double y6 = cenY + L2 * Math.sin(angle);
            double xOff = Math.cos(angle + 1.5707963267948966D) * 100.0D * (double)(i - 1);
            double yOff = Math.sin(angle + 1.5707963267948966D) * 100.0D * (double)(i - 1);
            CanvasUtils.drawLine(x0 + xOff, y0 + yOff, x1 + xOff, y1 + yOff, thickness, lineColor);
            CanvasUtils.drawLine(x1 + xOff, y1 + yOff, x2 + xOff, y2 + yOff, thickness, lineColor);
            CanvasUtils.drawLine(x2 + xOff, y2 + yOff, x4 + xOff, y4 + yOff, thickness, lineColor);
            CanvasUtils.drawLine(x4 + xOff, y4 + yOff, x5 + xOff, y5 + yOff, thickness, lineColor);
            CanvasUtils.drawLine(x5 + xOff, y5 + yOff, x6 + xOff, y6 + yOff, thickness, lineColor);
        }

        for(i = 0; i < DBSE.length; ++i) {
            if (totalTime - DBSE[i] > 0 && totalTime - DBSE[i] < 500) {
                CanvasUtils.drawRect1(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(255, 255, 255, 1.0D - (double)(totalTime - DBSE[i]) / 500.0D));
            }
        }

    }

    public static void BG5(int totalTime) {
        for(int i = 0; i < 33; ++i) {
            double x1 = 1200.0D * RANDOMS[i + 0];
            double x2 = 1200.0D * RANDOMS[i + 1];
            double y1 = 800.0D * RANDOMS[i + 2];
            double y2 = 1200.0D * RANDOMS[i + 3];
            double b = 3.141592653589793D / (3000.0D + 5000.0D * RANDOMS[i + 4]);
            double vMaxX = (x2 - x1) / 2.0D * b;
            double vMaxY = (y2 - y1) / 2.0D * b;
            double rad = 200.0D + 400.0D * RANDOMS[i + 5];
            CanvasUtils.drawCircle(Color.rgb(66, 66, 66, 0.1D), vMaxX / b * -Math.cos(b * (double)totalTime) + vMaxX / b + x1, vMaxY / b * -Math.cos(b * (double)totalTime) + vMaxY / b + y1, rad);
        }

    }

    public static void BG6(int totalTime) {
        int NUM_FLASHES = 10;

        for(int i = 1; i < FP.length; ++i) {
            if (totalTime < FP[i]) {
                if (counter < i * 10 && (double)(counter % 10) + RANDOMS[counter % 40] <= 1.0D * (double)(totalTime - FP[i - 1]) / (double)(FP[i] - FP[i - 1]) * 10.0D) {
                    Random rand = new Random();
                    CanvasUtils.drawImage2(Images.get("bg6.png"), rand.nextBoolean() ? -8.0D : 8.0D, rand.nextBoolean() ? -8.0D : 8.0D, 1200.0D, 800.0D);
                    CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(255, 0, 0, 0.1D));
                    ++counter;
                }
                break;
            }
        }

    }

    public static void BG7(int totalTime) {
        double SPEED = 0.08D;
        double offset = SPEED * (double)totalTime;
        boolean extraFirst = (int)(offset / 800.0D) % 2 == 1;
        double minus = -(offset % 800.0D);
        CanvasUtils.drawImage2(extraFirst ? Images.get("extra4.png") : Images.get("bg7.png"), 0.0D, minus, 1200.0D, 800.0D);
        CanvasUtils.drawImage2(!extraFirst ? Images.get("extra4.png") : Images.get("bg7.png"), 0.0D, minus + 800.0D, 1200.0D, 800.0D);
    }

    public static void BG8(int totalTime) {
    }

    public static void BG9(int totalTime) {
        CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(255, 0, 0, (double)totalTime / 100000.0D / 3.0D));
    }

    public static void BG10(int totalTime) {
        if (totalTime > 61938 && totalTime < 85828) {
            CanvasUtils.drawImage2(Images.get("extra8.png"), (double)(1200 - 1479 * (totalTime - '\uf1f2') / 23890), 80.0D, 279.0D, 100.0D);
        }

        double xOff1 = 500.0D + 250.0D * Math.cos((double)totalTime * 3.141592653589793D / 60000.0D);
        double xOff2 = 500.0D - 250.0D * Math.cos((double)totalTime * 3.141592653589793D / 40000.0D);
        double xOff3 = 400.0D - 250.0D * Math.cos((double)totalTime * 3.141592653589793D / 30000.0D);
        double[] shine1 = new double[]{-300.0D + xOff1, 820.0D, 100.0D + xOff1, 820.0D, 800.0D + xOff1, -20.0D, 400.0D + xOff1, -20.0D};
        double[] shine2 = new double[]{-100.0D + xOff2, 820.0D, -50.0D + xOff2, 820.0D, 650.0D + xOff2, -20.0D, 600.0D + xOff2, -20.0D};
        double[] shine3 = new double[]{-500.0D + xOff3, 820.0D, -400.0D + xOff3, 820.0D, 300.0D + xOff3, -20.0D, 200.0D + xOff3, -20.0D};
        double opa = totalTime > 98835 ? 0.4D : (totalTime > 97835 ? 0.25D + 0.15D * (double)(totalTime - 97835) / 1000.0D : 0.25D);
        CanvasUtils.drawPolygon(Color.rgb(255, 255, 145, opa), shine1);
        CanvasUtils.drawPolygon(Color.rgb(255, 255, 145, opa), shine2);
        if (totalTime > 97835) {
            CanvasUtils.drawPolygon(Color.rgb(255, 255, 145, opa - 0.25D), shine3);
            CanvasUtils.drawRect2(0.0D, 0.0D, 1200.0D, 800.0D, Color.rgb(255, 255, 145, 2.0D * (opa - 0.25D)));
        }

    }
}

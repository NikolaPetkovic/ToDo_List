package classes;

import controller.PrintController;

import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.OrientationRequested;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

public class Printer implements Printable {
    private PrintController printController;
    private ArrayList<Task> dataTasks;
    private ArrayList<Tag> dataTags;
    private int tasksPerPage = 18;
    private int[] pageBreaks;

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {

        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        Font font = new Font("Courier New", Font.PLAIN, 12);
        g2d.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int lineHeight = metrics.getHeight();

        if (pageBreaks == null) {
            int numBreaks = ((dataTasks.size()) / tasksPerPage); //Bei TODOLIST dataTasks.size/tasksPerPage;
            pageBreaks = new int[numBreaks];
            for (int b = 0; b < numBreaks; b++) {
                pageBreaks[b] = (b + 1) * tasksPerPage;
            }
        }

        if (pageIndex > pageBreaks.length) {
            return NO_SUCH_PAGE;
        }

        int y = 0;
        int skip = 0;

        int start;
        start = (pageIndex == 0) ? 0 : pageBreaks[pageIndex-1];
        int end;
        end = (pageIndex == pageBreaks.length)
                ? dataTasks.size() : pageBreaks[pageIndex];

        y+= lineHeight*2;
        g.drawString("ToDo List Print, from: "+printController.getFromDate()+" to: "+printController.getToDate(),10,y);
        y+=lineHeight;
        g.drawString(String.valueOf(pageIndex+1),550, 800);
        try{
        for (int line=start; line<end; line++) {
            y += lineHeight + skip*lineHeight;

            g.drawString("Task:     " + dataTasks.get(line).getShortName(), 10, y);
            g.drawString("Created:  " + dataTasks.get(line).getCreationDate().toString(true, false), 10, y+lineHeight);
            g.drawString("Deadline: " + dataTasks.get(line).getDeadline().toString(true, true), 10, y+lineHeight*2);
            g.drawString("Complet.: " + dataTasks.get(line).getCompletionDate().toString(true, true), 10, y+lineHeight*3);
            skip=4;
            if (dataTasks.get(line).gettagIdList().size() == 0) {
                g.drawString("Tags:     ", 10,y+lineHeight*4);
                skip=5;
            } else {
                skip+=dataTasks.get(line).gettagIdList().size();
                for (int j = 0; j < dataTasks.get(line).gettagIdList().size(); j++) {
                    for (int k = 0; k < dataTags.size(); k++) {
                        if (dataTasks.get(line).gettagIdList().get(j) == dataTags.get(k).getId()) {
                            if (j == 0) {
                                g.drawString("Tags:     " + dataTags.get(k).getShortName(), 10,y+lineHeight*4);

                            } else {
                                g.drawString("          " + dataTags.get(k).getShortName(), 10, y+lineHeight*4+lineHeight*k);
                            }
                        }
                    }
                }
            }
            if(dataTasks.size() - line != 0){
                line++;
                int move1 = 202;
                g.drawString("Task:     " + dataTasks.get(line).getShortName(), move1, y);
                g.drawString("Created:  " + dataTasks.get(line).getCreationDate().toString(true, false), move1, y+lineHeight);
                g.drawString("Deadline: " + dataTasks.get(line).getDeadline().toString(true, true), move1, y+lineHeight*2);
                g.drawString("Complet.: " + dataTasks.get(line).getCompletionDate().toString(true, true), move1, y+lineHeight*3);
                skip=4;
                if (dataTasks.get(line).gettagIdList().size() == 0) {
                    g.drawString("Tags:     ", move1,y+lineHeight*4);
                    skip=5;
                } else {
                    skip+=dataTasks.get(line).gettagIdList().size();
                    for (int j = 0; j < dataTasks.get(line).gettagIdList().size(); j++) {
                        for (int k = 0; k < dataTags.size(); k++) {
                            if (dataTasks.get(line).gettagIdList().get(j) == dataTags.get(k).getId()) {
                                if (j == 0) {
                                    g.drawString("Tags:     " + dataTags.get(k).getShortName(), move1,y+lineHeight*4);
                                } else {
                                    g.drawString("          " + dataTags.get(k).getShortName(), move1, y+lineHeight*4+lineHeight*k);
                                }
                            }
                        }
                    }
                }
            }

            if(dataTasks.size() - line != 0){
                line++;
                int move2 = 392;
                g.drawString("Task:     " + dataTasks.get(line).getShortName(), move2, y);
                g.drawString("Created:  " + dataTasks.get(line).getCreationDate().toString(true, false), move2, y+lineHeight);
                g.drawString("Deadline: " + dataTasks.get(line).getDeadline().toString(true, true), move2, y+lineHeight*2);
                g.drawString("Complet.: " + dataTasks.get(line).getCompletionDate().toString(true, true), move2, y+lineHeight*3);
                skip=4;
                if (dataTasks.get(line).gettagIdList().size() == 0) {
                    g.drawString("Tags:     ", move2,y+lineHeight*4);
                    skip=5;
                } else {
                    skip+=dataTasks.get(line).gettagIdList().size();
                    for (int j = 0; j < dataTasks.get(line).gettagIdList().size(); j++) {
                        for (int k = 0; k < dataTags.size(); k++) {
                            if (dataTasks.get(line).gettagIdList().get(j) == dataTags.get(k).getId()) {
                                if (j == 0) {
                                    g.drawString("Tags:     " + dataTags.get(k).getShortName(), move2,y+lineHeight*4);
                                } else {
                                    g.drawString("          " + dataTags.get(k).getShortName(), move2, y+lineHeight*4+lineHeight*k);
                                }
                            }
                        }
                    }
                }
            }

        }}
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        return PAGE_EXISTS;

    }

    public void printData(ArrayList<Task> tasks, ArrayList<Tag> tags) {
        dataTasks = new ArrayList<>();
        dataTasks.addAll(tasks);

        dataTags = new ArrayList<>();
        dataTags.addAll(tags);

                /* Create a print job */
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(this);
        if (pj.printDialog()) {
            try {
                pj.print();
            } catch (PrinterException pe) {
                System.err.println(pe);
            }
        }
    }
    public void setPrintController(PrintController pc){
        this.printController = pc;

    }
}
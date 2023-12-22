package com.github.curryp0mmes.tpll;


import com.github.curryp0mmes.tpll.config.ModConfigs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.logging.Level;
import java.util.logging.Logger;


// https://stackoverflow.com/questions/14226064/calling-a-method-when-content-of-clipboard-is-changed

public class ClipBoardListener extends Thread implements ClipboardOwner {

    Clipboard sysClip;
    @Override
    public void run() {
        System.setProperty("java.awt.headless", "false");
        sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable trans = sysClip.getContents(this);
        TakeOwnership(trans);

    }

    @Override
    public void lostOwnership(Clipboard c, Transferable t) {

        try {
            ClipBoardListener.sleep(250);  //waiting e.g for loading huge elements like word's etc.
        } catch(Exception e) {
            System.out.println("Exception: " + e);
        }
        Transferable contents = sysClip.getContents(this);
        try {
            process_clipboard(contents, c);
        } catch (Exception ex) {
            Logger.getLogger(ClipBoardListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        TakeOwnership(contents);


    }

    void TakeOwnership(Transferable t) {
        sysClip.setContents(t, this);
    }

    public void process_clipboard(Transferable t, Clipboard c) { //your implementation
        String clipboardContent;


    }

}
import tkinter as tk
import re
import urllib.request
import urllib.parse

root = tk.Tk()
enabled = tk.IntVar()
label2 = tk.Label(root, wraplength=200)

def send_clipboard(cbcontent):
    pattern = re.compile('.*\\d.*')
    pattern2 = re.compile('\\s*[0-9]*\\.[0-9]+.*[0-9]*\\.[0-9]+.*')
    if not pattern.match(cbcontent):
        return
    if not pattern2.match(cbcontent):
        return

    cbcontent = urllib.parse.quote(cbcontent)
    urllib.request.urlopen("http://localhost:7780/" + cbcontent)


def run_listener(window, interval, lastclip):
    if enabled.get() == 1:
        try:
            clip = window.clipboard_get()
            clip = clip.replace("\n", "")
        except tk.TclError:
            clip = lastclip

        if lastclip != clip:
            send_clipboard(clip)
            label2.config(text='CB: ' + ((clip[:150] + '\n...') if len(clip) > 75 else clip))
    else:
        clip = lastclip

    root.after(interval, run_listener, window, interval, clip)


if __name__ == '__main__':
    run_listener(root, 100, "")
    root.title("TPLL Clipboard Listener")
    root.geometry('200x200')

    label = tk.Label(root, bg='white', width=1000, text='BuildTheEarth Coordinate Listener')
    label.pack()

    enabled.set(1)
    c1 = tk.Checkbutton(root, text='Enable Clipboard Listen', variable=enabled, onvalue=1, offvalue=0)
    c1.pack()

    label2.pack()

    root.mainloop()



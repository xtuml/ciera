import SimpleHTTPServer
import SocketServer
import urlparse
import os
import json
import threading
import Queue
import time
import sys
import socket

PORT = 8080
runner = None

class XtumlRunner(threading.Thread):

  signals = None
  requester = None

  def __init__(self):
    super(XtumlRunner, self).__init__()
    self.signals = Queue.Queue()
    self.daemon = True
    self.requester = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    self.requester.connect(("0.0.0.0", 2003))

  def handleSignal(self, data):
    if (data["componentId"] == 3):
      self.requester.sendall(json.dumps(data["message"]) + "\n")
    else:
      out = open("signalData.json", "w")
      out.write(json.dumps(data))
      out.close()
      cmd = "./run_gps.sh --signal " + out.name
      rc = os.system(cmd)
      if (0 != rc):
        sys.exit(rc)

  def tick(self):
    cmd = "./run_gps.sh --heartbeat"
    rc = os.system(cmd)
    if (0 != rc):
      sys.exit(rc)

  def run(self):
    while (True):
      self.tick()
      while not self.signals.empty():
        self.handleSignal(self.signals.get())
      time.sleep(0.01)

class XtumlHttpHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):

  def do_GET(self):
    url_data = urlparse.urlparse(self.path)
    if (url_data.path == "/message"):
      if (None != runner):
        runner.signals.put(json.loads(urlparse.parse_qs(url_data.query)["data"][0]))
        self.send_response(200)
      else:
        self.send_response(500)
    else:
      self.send_response(404)

  def log_message(self, format, *args):
    pass

if __name__ == "__main__":
  runner = XtumlRunner()
  runner.start()
  httpd = SocketServer.TCPServer(("", PORT), XtumlHttpHandler)
  httpd.serve_forever()

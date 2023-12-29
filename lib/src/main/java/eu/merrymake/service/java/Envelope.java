package eu.merrymake.service.java;

import org.json.JSONObject;

public class Envelope {
  /**
   * Id of this particular message.
   * Note: it is _not_ unique, since multiple rivers can deliver the same message.
   * The combination of (river, messageId) is unique.
   */
  public final String messageId;
  /**
   * Id shared by all messages in the current trace, ie. stemming from the same
   * origin.
   */
  public final String traceId;
  /**
   * (Optional) Id corresponding to a specific originator. This id is rotated
   * occasionally, but in the short term it is unique and consistent. Same
   * sessionId implies the trace originated from the same device.
   */
  public final String sessionId;

  Envelope(String arg) {
    JSONObject json = new JSONObject(arg);
    this.messageId = json.getString("messageId");
    this.traceId = json.getString("traceId");
    this.sessionId = json.optString("sessionId");
  }
}

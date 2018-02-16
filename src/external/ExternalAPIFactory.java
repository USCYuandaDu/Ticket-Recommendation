
package external;

public class ExternalAPIFactory {
  private static final String DEFAULT_PIPELINE = "ticketmaster";

  // Start different APIs based on the pipeline.
  public static ExternalAPI getExternalAPI(String pipeline) {
    switch (pipeline) {
      case "ticketmaster":
        return new TicketMasterAPI();
      default:
        throw new IllegalArgumentException("Invalid pipeline " + pipeline);
    }
  }

  public static ExternalAPI getExternalAPI() {
    return getExternalAPI(DEFAULT_PIPELINE);
  }
}


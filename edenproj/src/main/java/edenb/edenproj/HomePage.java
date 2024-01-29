package edenb.edenproj;

import java.util.Date;

//import java.sql.Date;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("/home")
@PageTitle("Home")
public class HomePage extends VerticalLayout {
 
   private static final String CHAT_IMAGE_URL = "https://rb.gy/vi7fim";
   private String sessionId, userName;

    public HomePage(){
        System.out.println("\n=======> HomePage('/') constructor started...\n");

      // Get from Session the user-session-id & 'username' attribute 
      sessionId = VaadinSession.getCurrent().getSession().getId();
      userName = (String)VaadinSession.getCurrent().getSession().getAttribute("username");

      // if no 'username' attribute, this is a Guest.
      String welcomeMsg = "Welcome Guest!";
      if (userName != null)
         welcomeMsg = "Welcome " + userName.toUpperCase();

      // create image for chat page   
      Image imgChat = new Image(CHAT_IMAGE_URL, "chat image");
      imgChat.setHeight("200px");

      // Setup & Arrange UI components on this page
      Span textDate = new Span(new Date() + "");
      textDate.getStyle().setColor("blue");
      // textDate.getStyle().set("background-color", "#F00");

      // getStyle().setBackground("yellow");

      add(textDate);
      add(imgChat);
      add(new H1(welcomeMsg));
      add(new H3("( SessionID: " + sessionId + " )"));
      add(createLoginPanel()); // login field & buttons

      // set all components in the Center of page
      setSizeFull();
      setAlignItems(Alignment.CENTER);

      System.out.println("\n=======> HomePage('/') constructor ends!\n");
   }

   // create a panel with some login field & buttons.
   private HorizontalLayout createLoginPanel()
   {
      HorizontalLayout panel = new HorizontalLayout();
      panel.setAlignItems(Alignment.BASELINE);

      if (userName == null)
      {
         // This is a Guest - let him fill name-field & click a login-button the enter the chat.
         // The name MUST be two-words & one space between & with 4-15 letters. 
         TextField fieldName = new TextField("Your Name");
         fieldName.setPlaceholder("Enter your name");
         fieldName.setHelperText("This name will be your Chat-User");
         fieldName.setRequiredIndicatorVisible(true);
         fieldName.setErrorMessage("Name MUST be with two words, one space between, 4-15 Letters!");
         fieldName.setAllowedCharPattern("[a-zA-Z ]"); // only letters & spaces
         fieldName.setPattern("\\w+\\s\\w+"); // regx for two-words & one space between.  
         fieldName.setMinLength(4); // min 4 chars
         fieldName.setMaxLength(15); // max 15 chars
         fieldName.setPrefixComponent(VaadinIcon.USER.create()); // add user icon
         fieldName.setClearButtonVisible(true); // fast clear text (x)
         fieldName.setValueChangeMode(ValueChangeMode.LAZY); // eed for ChangeListener.  

         Button btnLogin = new Button("Login Chat", e -> loginChat(fieldName.getValue()));
         btnLogin.setEnabled(false);
         btnLogin.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

         // when user type his name, check name validation. 
         fieldName.addValueChangeListener(e -> btnLogin.setEnabled(!fieldName.isInvalid()));

         panel.add(fieldName, btnLogin);
      }
      else
      {
         // This is a Loged User - let him Go back to Chat, or Logout.
         Button btnGotoChat = new Button("Back to Chat", e -> routeToCanvasPage());
         btnGotoChat.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

         Button btnLogout = new Button("Logout", VaadinIcon.SIGN_OUT.create(), e -> logoutChat());
         btnLogout.setIconAfterText(true);
         btnLogout.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR); // RED button

         panel.add(btnGotoChat, btnLogout);
      }

      return panel;
    }

      // Login to chat by create 'username' attribute in the User-Session-Cookie & route to Chat Page
   private void loginChat(String value)
   {
      // Set Attribute 'username' with value, in Session Cookie.
      VaadinSession.getCurrent().getSession().setAttribute("username", value);

      // Redirect route to Chat Page.
      routeToCanvasPage();
   }

   // Logout from chat by Invalidate the User-Session
   private void logoutChat()
   {
      // Invalidate Session (delete the user-session-id and all its attributes)
      VaadinSession.getCurrent().getSession().invalidate();

      // Reload this page with new user-session-id
      UI.getCurrent().getPage().reload();
   }

   // Route to Chat Page ('/chat')
   private void routeToCanvasPage()
   {
      UI.getCurrent().getPage().setLocation("/Canvas");
   }
}

// ColorDialogFragment.java
// Allows user to set the drawing color on the DoodleView
package com.deitel.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import static android.R.attr.button;
import static android.R.attr.textColorAlertDialogListItem;
import static com.deitel.doodlz.R.id.LineRadioButton;
import static com.deitel.doodlz.R.id.cowRadioButton;
import static com.deitel.doodlz.R.id.emuRadioButton;
import static com.deitel.doodlz.R.id.goatRadioButton;
import static com.deitel.doodlz.R.id.screenBlankradioButton;

// class for the Select Color dialog
public class ColorDialogFragment extends DialogFragment {
   private SeekBar alphaSeekBar;
   private SeekBar redSeekBar;
   private SeekBar greenSeekBar;
   private SeekBar blueSeekBar;
   private View colorView;
   private int color;

   // create an AlertDialog and return it
   @Override
   public Dialog onCreateDialog(Bundle bundle) {
      // create dialog
      AlertDialog.Builder builder =
         new AlertDialog.Builder(getActivity());
      final View colorDialogView = getActivity().getLayoutInflater().inflate(
         R.layout.fragment_color, null);
      builder.setView(colorDialogView); // add GUI to dialog

      // set the AlertDialog's message
      builder.setTitle(R.string.title_color_dialog);

      // get the color SeekBars and set their onChange listeners
      alphaSeekBar = (SeekBar) colorDialogView.findViewById(
         R.id.alphaSeekBar);
      redSeekBar = (SeekBar) colorDialogView.findViewById(
         R.id.redSeekBar);
      greenSeekBar = (SeekBar) colorDialogView.findViewById(
         R.id.greenSeekBar);
      blueSeekBar = (SeekBar) colorDialogView.findViewById(
         R.id.blueSeekBar);
      colorView = colorDialogView.findViewById(R.id.colorView);

      // register SeekBar event listeners
      alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
      redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
      greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
      blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

      // use current drawing color to set SeekBar values
      final DoodleView doodleView = getDoodleFragment().getDoodleView();
      color = doodleView.getDrawingColor();
      alphaSeekBar.setProgress(Color.alpha(color));
      redSeekBar.setProgress(Color.red(color));
      greenSeekBar.setProgress(Color.green(color));
      blueSeekBar.setProgress(Color.blue(color));

      // drq:  update color here...add Set Color Button
      builder.setPositiveButton(R.string.button_set_color,
         new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

               // get status of color target selection radio button
               RadioButton targetColorButton = (RadioButton) colorDialogView.findViewById(LineRadioButton);
               if (targetColorButton.isChecked()) {
                   doodleView.setDrawingColor(color);
               }
               else {
                   doodleView.setScreenColor(color);
               }

               // check background selection
               RadioButton screen_rb = (RadioButton) colorDialogView.findViewById(screenBlankradioButton);
               if (screen_rb.isChecked()) {
                  doodleView.setScreenSelect(0);
               }


               RadioButton cow_rb = (RadioButton) colorDialogView.findViewById(cowRadioButton);
               if (cow_rb.isChecked()) {
                  doodleView.setScreenSelect(1);
               }


               RadioButton goat_rb = (RadioButton) colorDialogView.findViewById(goatRadioButton);
               if (cow_rb.isChecked()) {
                  doodleView.setScreenSelect(2);
               }


               RadioButton emu_rb = (RadioButton) colorDialogView.findViewById(emuRadioButton);
               if (emu_rb.isChecked()) {
                  doodleView.setScreenSelect(3);
               }


            }
         }
      );

      return builder.create(); // return dialog
   }

   // gets a reference to the MainActivityFragment
   private MainActivityFragment getDoodleFragment() {
      return (MainActivityFragment) getFragmentManager().findFragmentById(
         R.id.doodleFragment);
   }

   // tell MainActivityFragment that dialog is now displayed
   @Override
   public void onAttach(Activity activity) {
      super.onAttach(activity);
      MainActivityFragment fragment = getDoodleFragment();

      if (fragment != null)
         fragment.setDialogOnScreen(true);
   }

   // tell MainActivityFragment that dialog is no longer displayed
   @Override
   public void onDetach() {
      super.onDetach();
      MainActivityFragment fragment = getDoodleFragment();

      if (fragment != null)
         fragment.setDialogOnScreen(false);
   }

   // OnSeekBarChangeListener for the SeekBars in the color dialog
   private final OnSeekBarChangeListener colorChangedListener =
      new OnSeekBarChangeListener() {
         // display the updated color
         @Override
         public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {

            if (fromUser) // user, not program, changed SeekBar progress
               color = Color.argb(alphaSeekBar.getProgress(),
                  redSeekBar.getProgress(), greenSeekBar.getProgress(),
                  blueSeekBar.getProgress());
            colorView.setBackgroundColor(color);
         }

         @Override
         public void onStartTrackingTouch(SeekBar seekBar) {} // required

         @Override
         public void onStopTrackingTouch(SeekBar seekBar) {} // required
      };
}

/**************************************************************************
 * (C) Copyright 1992-2016 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/

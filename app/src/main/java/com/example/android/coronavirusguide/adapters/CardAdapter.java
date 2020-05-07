package com.example.android.coronavirusguide.adapters;

import android.animation.ObjectAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.android.coronavirusguide.R;
import com.example.android.coronavirusguide.data_models.CommonQuestionsData;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * This Class provides the Adapter to populate items/cards inside of the RecyclerView. It extends FirestoreRecyclerAdapter that
 * binds a Query to a RecyclerView and responds to all real-time events included items being added, removed, moved, or changed.
 */
public class CardAdapter extends FirestoreRecyclerAdapter<CommonQuestionsData, CardAdapter.CardHolder> {

    //Declaring an instance of mListener interface
    private OnItemClickListener mListener;

    /**
     * This constructor creates a new RecyclerView Adapter that listens to a Firestore Query.
     *
     * @param options FirestoreRecyclerOptions: for configuration options.
     */
    public CardAdapter(@NonNull FirestoreRecyclerOptions<CommonQuestionsData> options) {

        super(options);
    }

    /**
     * This method is called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the itemView to reflect the item at the given position.
     *
     * @param holder   CardHolder: The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position int: The position of the item within the adapter's data set.
     * @param model    SavedGame: is the data model that represents the user data
     */
    @Override
    protected void onBindViewHolder(@NonNull CardHolder holder, int position, @NonNull CommonQuestionsData model) {

        holder.question.setText(model.getQuestion());

        holder.answer.setText(model.getAnswer());
    }

    /**
     * This method is called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent   ViewGroup: The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType int: The view type of the new View.
     * @return CardHolder: A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_card, parent, false);

        return new CardHolder(view);
    }

    /**
     * This class represents a ViewHolder called CardHolder that describes an item view and metadata about its place within the RecyclerView.
     */
    class CardHolder extends RecyclerView.ViewHolder {

        //Declaring all Object Variables
        TextView question;

        ImageView viewButton;

        TextView answer;

        ImageView shareButton;

        RelativeLayout answerLayout;

        public CardHolder(@NonNull View itemView) {

            super(itemView);

            //Initializing all object variables
            question = itemView.findViewById(R.id.card_question_view);

            viewButton = itemView.findViewById(R.id.card_arrow_button);

            answer = itemView.findViewById(R.id.card_answer_view);

            shareButton = itemView.findViewById(R.id.share_button);

            answerLayout = itemView.findViewById(R.id.card_answer_layout);

            //Attaching an OnClickListener to the viewButton that determines the behavior that will happen when the user
            // clicks on that button
            viewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (answer.getVisibility() == View.GONE) {

                        // it's collapsed - expand it
                        answerLayout.setVisibility(View.VISIBLE);

                        answer.setVisibility(View.VISIBLE);

                        shareButton.setVisibility(View.VISIBLE);

                        viewButton.setImageResource(R.drawable.ic_collapse);

                    } else {

                        // it's expanded - collapse it
                        answerLayout.setVisibility(View.GONE);

                        answer.setVisibility(View.GONE);

                        shareButton.setVisibility(View.GONE);

                        viewButton.setImageResource(R.drawable.ic_expand);
                    }

                    //Declaring and initializing animation object variable
                    ObjectAnimator animation = ObjectAnimator.ofInt(answer, "maxLines", answer.getMaxLines());

                    //Sets the length of the animation on the animation Object and then call start()
                    animation.setDuration(200).start();
                }
            });

            //Attaching an OnClickListener to the shareButton that determines the behavior that will happen when the user
            // clicks on that button
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Get the Adapter position of the item represented by this CardHolder
                    int position = getAdapterPosition();

                    //call onItemClick method on the mListener and pass the position along with the documentSnapshot to the fragment because
                    //from the documentSnapshot we will recreate the whole CommonQuestionsData object, and get from it the data to create the message
                    //that will be shared
                    if (position != RecyclerView.NO_POSITION && mListener != null) {

                        mListener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    /**
     * This interface will be used to send data from the adapter to the underlying activity or fragment that implements this interface
     * It will be implemented in the CommonQuestionsFragment that displays the Common Questions' Cards.
     */
    public interface OnItemClickListener {

        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    /**
     * This method will be used in the CommonQuestionsFragment to set the OnItemClickListener to the CardAdapter
     *
     * @param listener CardAdapter.OnItemClickListener as an input parameter
     */
    public void setOnItemClickListener(OnItemClickListener listener) {

        mListener = listener;
    }
}
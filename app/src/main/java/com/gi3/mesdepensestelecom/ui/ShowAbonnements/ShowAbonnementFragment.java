package com.gi3.mesdepensestelecom.ui.ShowAbonnements;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.gi3.mesdepensestelecom.R;
import com.gi3.mesdepensestelecom.database.AbonnementRepository;
import java.util.List;

public class ShowAbonnementFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private ListView abonnementListView;
    private AbonnementRepository abonnementRepository;
    List<String> abonnements;

    public ShowAbonnementFragment() {
        // Constructeur vide requis
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_abonnements, container, false);

        sharedPreferences = requireContext().getSharedPreferences("user_session", MODE_PRIVATE);

        String displayName = sharedPreferences.getString("display_name", "");
        String userIdString = sharedPreferences.getString("user_id", "");
        int userId = Integer.parseInt(userIdString);
        abonnementListView = view.findViewById(R.id.abonnementListView);
        abonnementRepository = new AbonnementRepository(requireContext());

        // Récupérer les abonnements pour un utilisateur spécifique
        abonnements = abonnementRepository.getAllAbonnementsByUserId(userId);
        // Créer un adaptateur pour afficher les abonnements dans le ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, abonnements);

        // Définir l'adaptateur sur le ListView
        abonnementListView.setAdapter(adapter);

        FloatingActionButton fabAddAbonnement = view.findViewById(R.id.fabAddAbonnement);
        fabAddAbonnement.setOnClickListener(v -> {
            // Rediriger vers la page d'ajout d'abonnement
            navigateToAddAbonnementPage();
        });
        return view;
    }
    private void navigateToAddAbonnementPage() {
        // Ici, vous devez définir le code pour naviguer vers la page d'ajout d'abonnement.
        // Cela peut être une activité ou un fragment selon votre architecture.

        // Exemple (à adapter à votre code) pour naviguer vers une nouvelle activité :
        //  Intent intent = new Intent(requireContext(), com.gi3.mesdepensestelecom.ui.abonnement_form.AbonnementFragment.class);
        //  startActivity(intent);

        NavHostFragment.findNavController(ShowAbonnementFragment.this).navigate(R.id.nav_form_abonnement);
    }

}


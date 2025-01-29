package com.example.mystokapp.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mystokapp.StokKuApplications
import com.example.mystokapp.ui.viewModel.kategoriVM.KategoriDetailVM
import com.example.mystokapp.ui.viewModel.kategoriVM.KategoriHomeVM
import com.example.mystokapp.ui.viewModel.kategoriVM.KategoriInsertVM
import com.example.mystokapp.ui.viewModel.kategoriVM.KategoriUpdateVM
import com.example.mystokapp.ui.viewModel.merkVM.MerkDetailVM
import com.example.mystokapp.ui.viewModel.merkVM.MerkHomeVM
import com.example.mystokapp.ui.viewModel.merkVM.MerkInsertVM
import com.example.mystokapp.ui.viewModel.merkVM.MerkUpdateVM
import com.example.mystokapp.ui.viewModel.pemasokVM.PemasokDetailVM
import com.example.mystokapp.ui.viewModel.pemasokVM.PemasokHomeVM
import com.example.mystokapp.ui.viewModel.pemasokVM.PemasokInsertVM
import com.example.mystokapp.ui.viewModel.pemasokVM.PemasokUpdateVM
import com.example.mystokapp.ui.viewModel.produkVM.ProdukDetailVM
import com.example.mystokapp.ui.viewModel.produkVM.ProdukHomeVM
import com.example.mystokapp.ui.viewModel.produkVM.ProdukInsertVM
import com.example.mystokapp.ui.viewModel.produkVM.UpdateProdukVM


object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { ProdukHomeVM(aplikasiproduk().container.produkRepository) }
        initializer { ProdukInsertVM(aplikasiproduk().container.produkRepository) }
        initializer { ProdukDetailVM(aplikasiproduk().container.produkRepository) }
        initializer { UpdateProdukVM(aplikasiproduk().container.produkRepository) }

        initializer { PemasokHomeVM(aplikasiproduk().container.pemasokRepository) }
        initializer { PemasokInsertVM(aplikasiproduk().container.pemasokRepository) }
        initializer { PemasokDetailVM(aplikasiproduk().container.pemasokRepository) }
        initializer { PemasokUpdateVM(aplikasiproduk().container.pemasokRepository) }

        initializer { MerkHomeVM(aplikasiproduk().container.merkRepository) }
        initializer { MerkInsertVM(aplikasiproduk().container.merkRepository) }
        initializer { MerkDetailVM(aplikasiproduk().container.merkRepository) }
        initializer { MerkUpdateVM(aplikasiproduk().container.merkRepository) }

        initializer { KategoriHomeVM(aplikasiproduk().container.kategoriRepository) }
        initializer { KategoriInsertVM(aplikasiproduk().container.kategoriRepository) }
        initializer { KategoriDetailVM(aplikasiproduk().container.kategoriRepository) }
        initializer { KategoriUpdateVM(aplikasiproduk().container.kategoriRepository) }
    }
}

fun CreationExtras.aplikasiproduk(): StokKuApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as StokKuApplications)
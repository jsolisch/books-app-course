// Package main contains a android tutorial demo server. Note this is not a production ready serer.
package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"sync"
	"time"
)

// Book holds the book data.
type Book struct {
	Title  string `json:"title"`
	Author string `json:"author"`
}

var books = []Book{
	Book{Title: "Fahrenheit 451", Author: "Ray Bradbury"},
	Book{Title: "The Animal Farm", Author: "George Orwell"},
	Book{Title: "1984", Author: "George Orwell"},
}
var bookMux = sync.Mutex{}

func main() {
	mux := http.NewServeMux()
	mux.HandleFunc("/api/books", booksApi)
	srv := http.Server{
		Handler:      mux,
		Addr:         ":9001",
		WriteTimeout: 7 * time.Second,
		ReadTimeout:  7 * time.Second,
	}
	log.Printf("Starting server at: %s", srv.Addr)
	log.Printf("Error while running server, %v", srv.ListenAndServe())
}

func booksApi(w http.ResponseWriter, r *http.Request) {
	log.Printf("%s /api/books", r.Method)
	switch r.Method {
	case "GET":
		bookMux.Lock()
		defer bookMux.Unlock()
		json.NewEncoder(w).Encode(books)
	case "POST":
		bookMux.Lock()
		defer bookMux.Unlock()
		defer r.Body.Close()
		var book Book
		if err := json.NewDecoder(r.Body).Decode(&book); err != nil {
			http.Error(w, "No book given", http.StatusBadRequest)
		}
		for _, saved := range books {
			if saved.Title == book.Title && saved.Author == book.Author {
				w.WriteHeader(http.StatusCreated)
				return
			}
		}
		books = append(books, book)
	default:
		http.Error(w, fmt.Sprintf("Http method '%s' is not supported.", r.Method), http.StatusMethodNotAllowed)
	}
}

// Package main contains a android tutorial demo server. Note this is not a production ready serer.
package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"
	"strconv"
	"sync"
	"time"
)

// Book holds the book data.
type Book struct {
	Title    string `json:"title"`
	Author   string `json:"author"`
	ImageURL string `json:"imageUrl"`
	Modified int64  `json:"modified"`
}

var modified = time.Date(2021, time.April, 0, 0, 0, 0, 0, time.UTC).UnixNano() / 100000
var books = []Book{
	Book{Title: "Fahrenheit 451", Author: "Ray Bradbury", ImageURL: "http://covers.openlibrary.org/b/isbn/9780006546061-M.jpg", Modified: modified},
	Book{Title: "The Animal Farm", Author: "George Orwell", ImageURL: "http://covers.openlibrary.org/b/isbn/9780141036137-M.jpg", Modified: modified},
	Book{Title: "1984", Author: "George Orwell", ImageURL: "http://covers.openlibrary.org/b/isbn/0141036141-M.jpg", Modified: modified},
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
		since, _ := strconv.ParseInt(r.URL.Query().Get("since"), 10, 64)
		delta := make([]Book, 0)
		for _, book := range books {
			if book.Modified > since {
				delta = append(delta, book)
			}
		}
		json.NewEncoder(w).Encode(delta)
	case "POST":
		bookMux.Lock()
		defer bookMux.Unlock()
		defer r.Body.Close()
		var book Book
		if err := json.NewDecoder(r.Body).Decode(&book); err != nil {
			http.Error(w, "No book given", http.StatusBadRequest)
			log.Printf("POST err: %v", err)
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


provider "google" {
  credentials = file("devops-d4b98176f894.json")
  project     = var.project
  region      = var.region
  zone        = var.zone
}


resource "google_compute_instance" "vm_instance" {

  ## for a setup having multiple instances of the same type, you can do
  ## the following, there would be 2 instances of the same configuration
  ## provisioned
  count        = 2
  name         = "${var.instance-name}-${count.index}"


  machine_type = "f1-micro"

  boot_disk {
    initialize_params {
      image = "debian-cloud/debian-9"
    }
  }

  network_interface {
    # A default network is created for all GCP projects
    network       = "default"
    access_config {
    }
  }
}

// A variable for extracting the external ip of the instance
output "ip" {
 value = google_compute_instance.vm_instance[0].network_interface[0].access_config[0].nat_ip
}

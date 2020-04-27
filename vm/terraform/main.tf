
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


/*provisioner "local-exec" {
  command = "ansible-playbook -i '${self.public_ip},' --private-key ${var.ssh_key_private} ../ansible-apache/playbook/master.yml"
}


provisioner "remote-exec" {
  connection {
    type        = "ssh"
    user        = "devops"
    private_key = "${file(var.ssh_key_private)}"
  }
}*/